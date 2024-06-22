
package com.example.emssioncalculator.LogIn;

// Import necessary classes
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.Models.Car;
import com.example.emssioncalculator.Models.Cur_User;
import com.example.emssioncalculator.R;
import com.example.emssioncalculator.SignUp.SignUpActivity;
import com.example.emssioncalculator.UI.MainPageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    // Declare UI components and FirebaseAuth instance
    private Button btnLogIn;
    private TextView tvSignIn;
    private EditText edEmail, edPass;
    private CheckBox cbRem;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the superclass's onCreate method and set the content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize UI components
        cbRem = findViewById(R.id.cbRemeberMe);
        tvSignIn = findViewById(R.id.tvSignUp);
        btnLogIn = findViewById(R.id.btnLogin);
        edPass = findViewById(R.id.editTextPassword);
        edEmail = findViewById(R.id.editTextUsername);

        // Get shared preferences
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Check if user information should be remembered
        Boolean flag = sharedPref.getBoolean("userrem", false);
        if (flag) {
            // If yes, pre-fill the email and password fields
            edEmail.setText(sharedPref.getString("useremail", ""));
            edPass.setText(sharedPref.getString("userpass", ""));
            cbRem.setChecked(sharedPref.getBoolean("userrem", false));
        }

        // Set a click listener for the CheckBox
        cbRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the CheckBox is unchecked, clear the stored user information
                if (!cbRem.isChecked()) {
                    editor.putString("useremail", " ");
                    editor.putString("userpass", " ");
                    editor.putBoolean("userrem", false);
                    editor.apply();
                }
            }
        });


        // Set a click listener for the sign-up TextView
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the SignUpActivity
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Set a click listener for the log-in Button
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the email and password from the input fields
                String Email = edEmail.getText().toString();
                String Pass = edPass.getText().toString();

                // Check if the email and password are not empty
                if (!Pass.equals("") && !Email.equals("")) {
                    // If the CheckBox is checked, store the user information
                    if (cbRem.isChecked()) {
                        editor.putString("useremail", Email);
                        editor.putString("userpass", Pass);
                        editor.putBoolean("userrem", true);
                        editor.apply();
                    } else {
                        // If the CheckBox is unchecked, clear the stored user information
                        editor.putString("useremail", " ");
                        editor.putString("userpass", " ");
                        editor.putBoolean("userrem", false);
                        editor.apply();
                    }

                    // Sign in with email and password using FirebaseAuth
                    firebaseAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // If sign-in is successful, set the current user's email
                                Cur_User.email = Email;
                                // Retrieve the car information for the current user
                                FireBaseHelper fireBaseHelper = new FireBaseHelper();
                                fireBaseHelper.GetCar(new FireBaseHelper.IGetCar() {
                                    @Override
                                    public void OnGotCar(Car car) {
                                        // Set the current user's car and start the MainPageActivity
                                        Cur_User.car = car;
                                        Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                // If sign-in fails, display a toast message
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

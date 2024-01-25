package com.example.emssioncalculator.UI;

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

import com.example.emssioncalculator.R;
import com.example.emssioncalculator.repository.repository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button btnLogIn;
    private TextView tvSignIn;
    private EditText edEmail, edPass;
    private CheckBox cbRem;
    private MyDatabaseHelper db;
    private repository repository;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        cbRem = findViewById(R.id.cbRemeberMe);
        tvSignIn = findViewById(R.id.tvSignUp);
        btnLogIn = findViewById(R.id.btnLogin);
        edPass = findViewById(R.id.editTextPassword);
        edEmail = findViewById(R.id.editTextUsername);
        db = new MyDatabaseHelper(this);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Boolean flag = sharedPref.getBoolean("userrem", false);
        if (flag)
        {
            edEmail.setText(sharedPref.getString("useremail", ""));
            edPass.setText(sharedPref.getString("userpass", ""));
            cbRem.setChecked(sharedPref.getBoolean("userrem", false));
        }

        cbRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbRem.isChecked())
                {
                    editor.putString("useremail", " ");
                    editor.putString("userpass", " ");
                    editor.putBoolean("userrem", false);
                    editor.apply();
                }
            }
        });




        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = edEmail.getText().toString();
                String Pass = edPass.getText().toString();
                if (cbRem.isChecked())
                {
                    editor.putString("useremail", Email);
                    editor.putString("userpass", Pass);
                    editor.putBoolean("userrem", true);
                    editor.apply();
                }
                else
                {
                    editor.putString("useremail", " ");
                    editor.putString("userpass", " ");
                    editor.putBoolean("userrem", false);
                    editor.apply();
                }
                firebaseAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
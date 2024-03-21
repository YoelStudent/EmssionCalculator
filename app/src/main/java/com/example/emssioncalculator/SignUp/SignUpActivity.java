package com.example.emssioncalculator.SignUp;
import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.LogIn.MainActivity;
import com.example.emssioncalculator.repository.repository;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emssioncalculator.R;
import com.example.emssioncalculator.Models.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignUpActivity extends AppCompatActivity {
    private TextView tvSignIn;
    private Button btnSignUp;
    private EditText edEmail, edName, edPass, edAddress, edBirthDate;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        edEmail = findViewById(R.id.editTextEmail);
        edName = findViewById(R.id.editTextFullName);
        edPass = findViewById(R.id.editTextNewPassword);
        edAddress = findViewById(R.id.editTextAddress);
        edBirthDate = findViewById(R.id.editTextBirthDate);
        tvSignIn = findViewById(R.id.tvSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        repository = new repository(this);
        Context c = this;
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = edEmail.getText().toString();
                String Name = edName.getText().toString();
                String Pass = edPass.getText().toString();
                String Address = edAddress.getText().toString();
                String BirthDate = edBirthDate.getText().toString();
                User u = new User(Email, Name,Pass,Address,BirthDate);
                SignUp s = new SignUp(u,c);
                FireBaseHelper fb = new FireBaseHelper();

                if(s.Check_User()==0)
                {
                    Toast.makeText(c, "12312312312", Toast.LENGTH_SHORT).show();
                    repository.Add_User(u);

                }
                Toast.makeText(c, "asdasdasdasd", Toast.LENGTH_SHORT).show();





                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
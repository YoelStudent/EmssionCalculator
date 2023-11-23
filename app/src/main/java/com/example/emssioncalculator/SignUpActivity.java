package com.example.emssioncalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    private TextView tvSignIn;
    private Button btnSignUp;
    private EditText edEmail, edName, edPass, edAddress, edBirthDate;
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
        MyDatabaseHelper db = new MyDatabaseHelper(this);

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
                Boolean emailExists = db.checkExists(Email);
                if (!emailExists)
                {
                    db.addItem(Name,Email,Pass,Address,BirthDate);
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
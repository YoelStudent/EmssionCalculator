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
                User u = new User(edEmail.getText().toString(),edName.getText().toString(),edPass.getText().toString(),edAddress.getText().toString(), edBirthDate.getText().toString());

                SignUp s = new SignUp(u,c);
                FireBaseHelper fb = new FireBaseHelper();

                if(s.Check_User()==0)
                {
                    Toast.makeText(c, "user added", Toast.LENGTH_SHORT).show();
                    repository.Add_User(u);

                }


                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
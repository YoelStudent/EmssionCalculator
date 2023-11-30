package com.example.emssioncalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Button btnLogIn;
    private TextView tvSignIn;
    private EditText edEmail, edPass;
    private MyDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://greencalc-ad9e8-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Email");

        myRef.setValue("yoel@gmail.com");

        tvSignIn = findViewById(R.id.tvSignUp);
        btnLogIn = findViewById(R.id.btnLogin);
        edPass = findViewById(R.id.editTextPassword);
        edEmail = findViewById(R.id.editTextUsername);
        db = new MyDatabaseHelper(this);
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
                Boolean logInResult = db.CheckLogIn(Email, Pass);
                if (logInResult)
                {
                    Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "failed login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
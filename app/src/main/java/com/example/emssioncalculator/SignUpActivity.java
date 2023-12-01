package com.example.emssioncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private TextView tvSignIn;
    private Button btnSignUp;
    private EditText edEmail, edName, edPass, edAddress, edBirthDate;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
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
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://greencalc-ad9e8-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
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

                }
//                user.setName(Name);
//                user.setEmail(Email);
//                user.setPass(Pass);


              //  String id;
                //databaseReference.child("user").setValue(Email);
                firebaseAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        User user = new User(Name,Email);

                        String id = firebaseAuth.getCurrentUser().getUid();
                        databaseReference.child("users").child(id).child("Email").setValue(Email);
                        databaseReference.child("users").child(id).child("Name").setValue(Name);
                        databaseReference.child("users").child(id).child("Pass").setValue(Pass);

                    }
                });


                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
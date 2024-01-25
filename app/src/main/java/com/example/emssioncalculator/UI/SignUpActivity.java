package com.example.emssioncalculator.UI;
import com.example.emssioncalculator.Models.SignUp;
import com.example.emssioncalculator.repository.repository;
import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private TextView tvSignIn;
    private Button btnSignUp;
    private EditText edEmail, edName, edPass, edAddress, edBirthDate;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private repository repository;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
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
        databaseReference = database.getReference();
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
                if(s.Check_User()==0)
                {
                    Boolean emailExists = db.checkExists(Email);
                    if (!emailExists) {
                        db.addItem(Name, Email, Pass, Address, BirthDate);

                    }
                }


                firebaseAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignUpActivity.this, "DONEEEEEEEE.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    User user1 = new User(Email, Name, Pass, Address, BirthDate);
                                    databaseReference.child("users").child(user.getUid()).child("Email").setValue(Email);
                                    databaseReference.child("users").child(user.getUid()).child("Email").setValue(Name);
                                    databaseReference.child("users").child(user.getUid()).child("Pass").setValue(Pass);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
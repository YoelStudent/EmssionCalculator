package com.example.emssioncalculator.DB;

import androidx.annotation.NonNull;

import com.example.emssioncalculator.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseHelper {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    public FireBaseHelper()
    {

    }
    public int AddUser(User u)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference();
        firebaseAuth.createUserWithEmailAndPassword(u.getEmail(), u.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    databaseReference.child("users").child(user.getUid()).child("Email").setValue(u.getEmail());
                    databaseReference.child("users").child(user.getUid()).child("Name").setValue(u.getName());
                    databaseReference.child("users").child(user.getUid()).child("Pass").setValue(u.getPass());
                }
                else{

                }
            }
        });
        return 0;
    }
}

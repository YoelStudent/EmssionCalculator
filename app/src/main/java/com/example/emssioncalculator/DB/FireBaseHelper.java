package com.example.emssioncalculator.DB;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.emssioncalculator.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FireBaseHelper {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;

    public FireBaseHelper()
    {

    }
    interface OnFetch
    {
        void OnComplete(Task<QuerySnapshot> task);
    }
    public void RetrieveDoc(OnFetch onFetch){
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        database.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    onFetch.OnComplete(task);
                }
            }
        });
    }
    public void UpdateUser(User u,  ProgressDialog pd)
    {


        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

       RetrieveDoc(new OnFetch() {
           @Override
           public void OnComplete(Task<QuerySnapshot> task) {
               for (QueryDocumentSnapshot document : task.getResult())
               {
                   String s =firebaseAuth.getCurrentUser().getEmail();
                   if(document.getData().get("email").equals(s))
                   {

                       // Add a new document with a generated ID
                       firebaseAuth.getCurrentUser().verifyBeforeUpdateEmail(u.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()) {
                                   database.collection("users")
                                           .document(document.getId()).update(u.toHashMap())
                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   if (task.isSuccessful()) {
                                                       pd.dismiss();
                                                   }
                                               }
                                           })
                                           .addOnFailureListener(new OnFailureListener() {
                                               @Override
                                               public void onFailure(@NonNull Exception e) {
                                               }
                                           });

                               }
                           }
                       });

                   }
               }
           }
       });
    }
    public interface IGetUser{
        void OnGotUser(User user);
    }
    public void GetUser(IGetUser iGetUser){

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        RetrieveDoc(new OnFetch() {
            @Override
            public void OnComplete(Task<QuerySnapshot> task) {
                String s =firebaseAuth.getCurrentUser().getEmail();

                for (QueryDocumentSnapshot document : task.getResult())
                {
                    if(document.getData().get("email").toString().equals(s))
                    {
                        User u = new User(document.getData().get("email").toString(), document.getData().get("name").toString(), document.getData().get("password").toString(), document.getData().get("address").toString(),document.getData().get("age").toString());
                        if (true){

                        }
                        iGetUser.OnGotUser(u);
                    }
                }
            }
        });
    }
//    public int AddUser(User u)
//    {
//        firebaseAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//
//        databaseReference = database.getReference();
//        firebaseAuth.createUserWithEmailAndPassword(u.getEmail(), u.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    // Sign in success, update UI with the signed-in user's information
//
//                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                    databaseReference.child("users").child(user.getUid()).child("Email").setValue(u.getEmail());
//                    databaseReference.child("users").child(user.getUid()).child("Name").setValue(u.getName());
//                    databaseReference.child("users").child(user.getUid()).child("Pass").setValue(u.getPass());
//                }
//                else{
//
//                }
//            }
//        });
//        return 0;
//    }
    public void AddUser(User u){
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();


        firebaseAuth.createUserWithEmailAndPassword(u.getEmail(), u.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    // Add a new document with a generated ID
                    database.collection("users")
                            .add(u.toHashMap())
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                }
                else{

                }
            }
        });

    }
}

package com.example.emssioncalculator.DB;

// Import necessary classes and methods


import androidx.annotation.NonNull;

import com.example.emssioncalculator.Models.Cur_User;
import com.example.emssioncalculator.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.example.emssioncalculator.Models.Car;

import java.util.HashMap;


public class FireBaseHelper {
    // Declare FirebaseAuth and FirebaseFirestore instances
    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;

    // Constructor for FireBaseHelper
    public FireBaseHelper() {}

    // Interface for fetch completion callback
    interface OnFetch {
        void OnComplete(Task<QuerySnapshot> task);
    }

    // Method to retrieve documents from the Firestore collection "users"
    public void RetrieveDoc(OnFetch onFetch){
        // Initialize FirebaseAuth and FirebaseFirestore instances
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        // Get the "users" collection and add an OnCompleteListener
        database.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // If the task is successful, call the OnComplete method of the OnFetch interface
                if (task.isSuccessful()){
                    onFetch.OnComplete(task);
                }
            }
        });
    }

    // Interface for getting car information callback
    public interface IGetCar {
        void OnGotCar(Car car);
    }

    // Method to get car information for the current user
    public void GetCar(IGetCar iGetCar){
        // Initialize FirebaseAuth and FirebaseFirestore instances
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        // Retrieve documents and get car information
        RetrieveDoc(new OnFetch() {
            @Override
            public void OnComplete(Task<QuerySnapshot> task) {
                // Get the current user's email
                String s = firebaseAuth.getCurrentUser().getEmail();

                // Loop through the retrieved documents
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Check if the email in the document matches the current user's email
                    if(document.getData().get("email").toString().equals(s)) {
                        // Get the "car" subcollection for the user and add an OnCompleteListener
                        database.collection("users")
                                .document(document.getId()).collection("car").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        // Get the first document in the "car" collection
                                        DocumentSnapshot q = task.getResult().getDocuments().get(0);
                                        // Cast the document data to a HashMap
                                        HashMap hashMap = (HashMap) q.getData();
                                        // Call the OnGotCar method with the retrieved car information
                                        iGetCar.OnGotCar(new Car(hashMap.get("make").toString(), hashMap.get("model").toString(), hashMap.get("year").toString(), Integer.parseInt(hashMap.get("mpg").toString()), hashMap.get("fuelType").toString()));
                                    }
                                });
                    }
                }
            }
        });
    }

    // Method to update car information for the current user
    public void UpdateCar(Car c) {
        // Initialize FirebaseAuth and FirebaseFirestore instances
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        // Retrieve documents and update car information
        RetrieveDoc(new OnFetch() {
            @Override
            public void OnComplete(Task<QuerySnapshot> task) {
                // Loop through the retrieved documents
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Check if the email in the document matches the current user's email
                    if (document.getData().get("email").toString().equals(Cur_User.email)) {
                        // Get the "car" subcollection for the user and add an OnCompleteListener
                        database.collection("users").document(document.getId()).collection("car").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                // Update the car document with the new car information
                                database.collection("users").document(document.getId()).collection("car").document(task.getResult().getDocuments().get(0).getId()).update(c.toHashMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            // Car update was successful
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    }
                }
            }
        });
    }

    // Interface for adding a user callback
    public interface IAddUser {
        void OnAddUser();
    }

    // Method to add a new user
    public void AddUser(User u, IAddUser iAddUser){
        // Initialize FirebaseAuth and FirebaseFirestore instances
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        // Create a new user with email and password and add an OnCompleteListener
        firebaseAuth.createUserWithEmailAndPassword(u.getEmail(), u.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // If user creation is successful, add the user information to the Firestore collection "users"
                    database.collection("users")
                            .add(u.toHashMap())
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    // Call the OnAddUser method to indicate success
                                    iAddUser.OnAddUser();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure
                                }
                            });
                }
            }
        });
    }

    // Interface for adding a car callback
    public interface IAddCar {
        void OnAddCar();
    }

    // Method to add a car for the current user
    public void AddCar(Car c, IAddCar iAddCar) {
        // Initialize FirebaseAuth and FirebaseFirestore instances
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        // Retrieve documents and add car information
        RetrieveDoc(new OnFetch() {
            @Override
            public void OnComplete(Task<QuerySnapshot> task) {
                // Loop through the retrieved documents
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Check if the email in the document matches the current user's email
                    if (document.getData().get("email").toString().equals(Cur_User.email)) {
                        // Add the car information to the "car" subcollection for the user
                        database.collection("users").document(document.getId()).collection("car").add(c.toHashMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                // Call the OnAddCar method to indicate success
                                iAddCar.OnAddCar();
                            }
                        });
                        break;
                    }
                }
            }
        });
    }

    // Method to update user information
    public void UpdateUser(User u) {
        // Initialize FirebaseAuth and FirebaseFirestore instances
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        // Retrieve documents and update user information
        RetrieveDoc(new OnFetch() {
            @Override
            public void OnComplete(Task<QuerySnapshot> task) {
                // Loop through the retrieved documents
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Check if the email in the document matches the current user's email
                    if(document.getData().get("email").toString().equals(Cur_User.email)) {
                        // Update the user document with the new user information
                        database.collection("users")
                                .document(document.getId()).update(u.toHashMap())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // If the update is successful, update the user's password
                                            firebaseAuth.getCurrentUser().updatePassword(u.getPass());
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure
                                    }
                                });
                    }
                }
            }
        });
    }

    // Interface for getting user information callback
    public interface IGetUser {
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
                        User u = new User(document.getData().get("email").toString(), document.getData().get("name").toString(), document.getData().get("password").toString(),document.getData().get("age").toString());
                        iGetUser.OnGotUser(u);
                    }
                }
            }
        });
    }
    public void FcheckEmailExistence(String email, Check_Email callback) {
        database = FirebaseFirestore.getInstance();

        database.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean usernameExists = false;
                boolean emailExists = false;

                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.getData().get("email").toString().equals(email)) {
                        emailExists = true;
                    }
                }
                callback.onEmailCheckCom(emailExists);
            }
        });
    }
    public interface Check_Email {
        /**
         * Called when the credentials check is complete.
         *
         * @param doesUserExist True if the user exists, false otherwise.
         * @param doesEmailExist True if the email exists, false otherwise.
         */
        void onEmailCheckCom(boolean doesEmailExist);
    }








}

package com.example.emssioncalculator.repository;

import android.content.Context;
import android.widget.Toast;

import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.DB.MyDatabaseHelper;
import com.example.emssioncalculator.Models.Cur_User;
import com.example.emssioncalculator.Models.User;
import com.google.firebase.auth.FirebaseAuth;

public class repository {

    private FireBaseHelper fb;
    Context context;
    public repository(Context context)
    {
        fb = new FireBaseHelper();
        this.context = context;
    }
        public void Add_User(User user, FireBaseHelper.IAddCar callback)
        {

            fb.FcheckEmailExistence(user.getEmail(), new FireBaseHelper.Check_Email() {
                @Override
                public void onEmailCheckCom(boolean doesEmailExist) {
                    if (!doesEmailExist)
                    {
                        fb.AddUser(user, new FireBaseHelper.IAddUser() {
                            @Override
                            public void OnAddUser() {
                                fb.AddCar(Cur_User.car, callback);
                            }
                        });
                    }
                    else 
                    {
                        Toast.makeText(context, "Email already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}

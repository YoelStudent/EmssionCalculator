package com.example.emssioncalculator.repository;

import android.content.Context;

import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.DB.MyDatabaseHelper;
import com.example.emssioncalculator.Models.Cur_User;
import com.example.emssioncalculator.Models.User;

public class repository {

    private FireBaseHelper fb;
    public repository(Context context)
    {
        fb = new FireBaseHelper();
    }
    public void Add_User(User user)
    {
       //TODO: CHECK IF EMAIL UNIQUE
        fb.AddUser(user, new FireBaseHelper.IAddUser() {
            @Override
            public void OnAddUser() {
                fb.AddCar(Cur_User.car);
            }
        });

    }

}

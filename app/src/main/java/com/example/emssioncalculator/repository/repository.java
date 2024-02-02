package com.example.emssioncalculator.repository;

import android.content.Context;

import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.DB.MyDatabaseHelper;
import com.example.emssioncalculator.Models.User;

public class repository {
    private MyDatabaseHelper db;

    private FireBaseHelper fb;
    public repository(Context context)
    {
        db = new MyDatabaseHelper(context);
        fb = new FireBaseHelper();
    }
    public void Add_User(User user)
    {
        if(!db.checkExists(user.getEmail()))
        {
            fb.AddUser(user);
            db.addItem(user.getName(), user.getEmail(), user.getPass(), user.getAddress(), user.getAge());
        }
    }

}

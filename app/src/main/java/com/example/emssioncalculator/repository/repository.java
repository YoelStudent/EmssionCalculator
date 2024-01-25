package com.example.emssioncalculator.repository;

import android.content.Context;

import com.example.emssioncalculator.UI.MyDatabaseHelper;
import com.example.emssioncalculator.Models.User;

public class repository {
    private MyDatabaseHelper db;
    public repository(Context context)
    {
        db = new MyDatabaseHelper(context);
    }
    public void Add_User(User user)
    {
        if(db.checkExists(user.getEmail()))
        {
            db.addItem(user.getName(), user.getEmail(), user.getPass(), user.getAddress(), user.getAge());
        }
    }

}

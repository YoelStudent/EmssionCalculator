package com.example.emssioncalculator.Models;

import android.content.Context;

enum error_codes {EMAIL, NAME,PASS, ADDRESS,AGE};
public class SignUp {
    User user;
    Context context;
    public SignUp(User u, Context c)
    {
        this.user = u;
        this.context = c;
    }
    public int Check_User()
    {
        if (user.getEmail().length() > 320)
        {
            return 1;
        }
        if (user.getPass().length() >= 6 &&user.getPass().length() <= 20)
        {
            return 2;
        }
        if (user.getName().length() >= 2 && user.getName().length() <= 20)
        {
            return 3;
        }
        return 0;
    }
}

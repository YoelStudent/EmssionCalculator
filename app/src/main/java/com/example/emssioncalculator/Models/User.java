package com.example.emssioncalculator.Models;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String Email;
    private String Pass;
    private String Name;
    private String Age;
    public User(String Email, String Name,String Pass,  String Age)
    {
        this.Email = Email;
        this.Pass = Pass;
        this.Name = Name;
        this.Age = Age;
    }
    public int Check_User()
    {
        if (!isValidName(Name))
        {
            return 2;
        } else if (!isValidPassword(Pass)) {
         return 3;
        } else if (!isValidAge(Integer.parseInt(Age))) {
         return 4;
        } else {
            // Proceed with sign-up
            return 0;
        }
    }



    public static boolean isValidName(String name) {
        // Check if the name contains only English characters
        return name.matches("[a-zA-Z ]+");
    }

    public static boolean isValidPassword(String password) {
        // Check if password length is between 6 and 20 characters
        return password.length() >= 6 && password.length() <= 20;
    }

    public static boolean isValidAge(int age) {
        // Check if age is between 16 and 100
        return age >= 16 && age <= 100;
    }


    public String getEmail() {
        return Email;
    }

    public String getPass() {
        return Pass;
    }


    public String getAge() {
        return Age;
    }

    public String getName() {
        return Name;
    }

    public Map<String, Object> toHashMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("name", Name);
        map.put("email", Email);
        map.put("age", Age);
        map.put("password", Pass);
        return map;
    }
}

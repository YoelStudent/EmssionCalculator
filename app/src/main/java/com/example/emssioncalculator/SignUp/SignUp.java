package com.example.emssioncalculator.SignUp;

import android.content.Context;
import android.widget.Toast;

import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.Models.User;

;
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
        if (!isValidEmail(user.getEmail())) {
            showToast("Invalid email format");
        } else if (!isValidName(user.getName())) {
            showToast("Name must contain only English characters");
        } else if (!isValidPassword(user.getPass())) {
            showToast("Password must be between 6 and 20 characters");
        } else if (!isValidAge(Integer.parseInt(user.getAge()))) {
            showToast("Age must be between 16 and 100");
        } else {
            // Proceed with sign-up
            return 0;
        }
        return 1;
    }

    public static boolean isValidEmail(String email) {
        // Simple email validation using regex
        int atIndex = email.indexOf('@');
        int dotIndex = email.lastIndexOf('.');
        return atIndex > 0 && dotIndex > atIndex && dotIndex < email.length() - 1;
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

    public void showToast(String message) {
        // Simulating a toast message
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

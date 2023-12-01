package com.example.emssioncalculator;

public class User {
    public String Email;
    //public String Pass;
    public String Name;
    public User(String Email, String Name)
    {
        this.Email = Email;
      //  this.Pass = Pass;
        this.Name = Name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    //public void setPass(String pass) {
           // Pass = pass;
  //  }

    public void setName(String name) {
        Name = name;
    }
}

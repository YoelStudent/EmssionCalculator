package com.example.emssioncalculator;

public class User {
    private String Email;
    private String Pass;
    private String Name;
    private String Address;
    private String Age;
    public User(String Email, String Name,String Pass, String Address, String Age)
    {
        this.Email = Email;
        this.Pass = Pass;
        this.Name = Name;
        this.Address = Address;
        this.Age = Age;
    }

    public String getEmail() {
        return Email;
    }

    public String getPass() {
        return Pass;
    }

    public String getAddress() {
        return Address;
    }

    public String getAge() {
        return Age;
    }

    public String getName() {
        return Name;
    }
}

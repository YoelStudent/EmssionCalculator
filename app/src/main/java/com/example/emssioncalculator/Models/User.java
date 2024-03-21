package com.example.emssioncalculator.Models;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Object> toHashMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("name", Name);
        map.put("email", Email);
        map.put("age", Age);
        map.put("address", Address);
        map.put("password", Pass);
        return map;
    }
}

package com.example.emssioncalculator.Models;

import java.util.HashMap;
import java.util.Map;

public class Car {
    private String make;
    private String model;
    private String year;
    private int mpg;
    private String fuelType;

    // Constructor
    public Car(String make, String model, String year, int mpg, String fuelType) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.mpg = mpg;
        this.fuelType = fuelType;
    }
    public Car(){

    }

    // Getters and setters
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getMpg() {
        return mpg;
    }

    public void setMpg(int mpg) {
        this.mpg = mpg;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    // Override toString() method for easy printing
    @Override
    public String toString() {
        return year + " " + make + " " + model + " (" + fuelType + "), MPG: " + mpg;
    }
    public Map<String, Object> toHashMap() {
        Map<String, Object> carMap = new HashMap<>();
        carMap.put("make", make);
        carMap.put("model", model);
        carMap.put("year", year);
        carMap.put("mpg", mpg);
        carMap.put("fuelType", fuelType);
        return carMap;
    }

    // Example usage

}

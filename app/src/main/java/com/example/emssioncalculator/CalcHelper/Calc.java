
package com.example.emssioncalculator.CalcHelper;

// Import static method sleep from the Thread class
import static java.lang.Thread.sleep;

// Import necessary classes
import android.location.Location;
import com.example.emssioncalculator.Models.Car_Lock;
import com.example.emssioncalculator.Models.Dis;
import com.example.emssioncalculator.Http.CarsHttp;
import com.example.emssioncalculator.Http.HTTPreq;
import com.google.android.libraries.places.api.model.Place;


public class Calc {
    // Method to get distance between a given location and destination place
    public void getDistance(Location location, Place des) {
        // Build URL for the distance matrix API request
        String u = "https://maps.googleapis.com/maps/api/distancematrix/json?destinations="
                + des.getLatLng().latitude + "," + des.getLatLng().longitude
                + "&origins=" + location.getLatitude() + "," + location.getLongitude()
                + "&units=metric&travelmode=driving&key=";
        // Append the API key to the URL
        u += "AIzaSyCaYF1IVpeCYRV5H75bWNKE2JmgFOGFK1k";

        // Create a new HTTPreq object
        HTTPreq httPreq = new HTTPreq();
        // Set the URL for the HTTP request
        httPreq.SetString(u);
        // Execute the HTTP request
        httPreq.use();

        // Initialize a timer variable
        int timer = 0;

        // Wait for the Dis.lock to be released
        while (Dis.lock){
            try {
                // Sleep for 2 seconds
                sleep(2000);
            } catch (InterruptedException e) {
                // Handle the InterruptedException
                throw new RuntimeException(e);
            }
            // Increment the timer
            timer++;

            // Check if the timer has reached 4 iterations
            if (timer == 4) {
                // Set valid_place to false and break the loop
                Dis.valid_place = false;
                break;
            }
        }
        // Reset Dis.lock to true
        Dis.lock = true;
    }

    // Method to get car information based on make, model, and year
    public void getCar(String Car, String Model, String Year) {
        // Build URL for the car API request
        String u = "https://api.api-ninjas.com/v1/cars?limit=2&make=" + Car
                + "&model=" + Model + "&year=" + Year + "&X-Api-Key=";
        // Append the API key to the URL
        u += "JbRMmeIXk1MJts6VqxnQH08904D2zelSzoO3kMEY";

        // Create a new CarsHttp object
        CarsHttp httPreq = new CarsHttp();
        // Set the URL for the HTTP request
        httPreq.SetString(u);
        // Execute the HTTP request
        httPreq.use();

        // Initialize a timer variable
        int timer = 0;

        // Wait for the Car_Lock.flag to be released
        while (Car_Lock.flag){
            try {
                // Sleep for 2 seconds
                sleep(2000);
            } catch (InterruptedException e) {
                // Handle the InterruptedException
                throw new RuntimeException(e);
            }
            // Increment the timer
            timer++;
            // Check if the timer has reached 4 iterations
            if (timer == 4) {
                // Set valid_car to false and break the loop
                Car_Lock.valid_car = false;
                break;
            }
        }
        // Reset Car_Lock.flag to true
        Car_Lock.flag = true;
    }
}

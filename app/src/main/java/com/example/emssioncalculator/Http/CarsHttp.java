
package com.example.emssioncalculator.Http;

// Import necessary classes

import androidx.fragment.app.Fragment;

import com.example.emssioncalculator.Models.Car;
import com.example.emssioncalculator.Models.Car_Lock;
import com.example.emssioncalculator.Models.Cur_User;
import com.example.emssioncalculator.UI.CalcEmm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalcEmm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarsHttp implements Runnable {
    // URL string for the HTTP request
    private String u;
    // Static variable to store the distance result
    public static String distance_ret = "";

    // Method to set the URL string
    public void SetString(String url) {
        u = url;
    }

    // Method to start a new thread and execute the run method
    public void use() {
        Thread thread = new Thread(this);
        thread.start();
    }

    // Override the run method to execute the HTTP request
    @Override
    public void run() {
        try {
            // Initialize variables for the response, connection, and reader
            String response = "";
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            // Create a URL object from the URL string
            URL url = new URL(u);
            // Open an HTTP connection to the URL
            connection = (HttpURLConnection) url.openConnection();
            // Connect to the URL
            connection.connect();

            // Get the input stream from the connection
            InputStream stream = connection.getInputStream();
            // Create a BufferedReader to read the input stream
            reader = new BufferedReader(new InputStreamReader(stream));

            // Use a StringBuilder to accumulate the response
            StringBuilder buffer = new StringBuilder();
            String line = "";

            // Read the input stream line by line and append to the buffer
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            // Convert the buffer to a string and parse it to a JSON array
            if (buffer != null) {
                JSONArray json = new JSONArray(buffer.toString());
                // Check if the JSON array is not empty
                if (!json.toString().equals("[]")) {
                    // Get the first JSON object from the array
                    JSONObject jsonObject = json.getJSONObject(0);

                    // Extract car information from the JSON object
                    Integer mpg = Integer.parseInt(jsonObject.getString("city_mpg"));
                    String fuelType = jsonObject.getString("fuel_type");
                    String make = jsonObject.getString("make");
                    String model = jsonObject.getString("model");
                    String year = jsonObject.getString("year");

                    // Set the car information in the Cur_User object
                    Cur_User.car = new Car(make, model, year, mpg, fuelType);

                    // Update the Car_Lock flags
                    Car_Lock.flag = false;
                    Car_Lock.valid_car = true;
                } else {
                    // If the JSON array is empty, set the Car_Lock flags to indicate invalid car
                    Car_Lock.flag = false;
                    Car_Lock.valid_car = false;
                }
            }
        } catch (MalformedURLException e) {
            // Handle MalformedURLException
            e.printStackTrace();
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        } catch (JSONException e) {
            // Handle JSONException
            throw new RuntimeException(e);
        }
    }

    // Method to get the distance result
    public String Get_Distance() {
        return distance_ret;
    }
}

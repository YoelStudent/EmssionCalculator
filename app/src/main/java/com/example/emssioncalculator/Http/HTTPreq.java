
package com.example.emssioncalculator.Http;

// Import necessary classes

import com.example.emssioncalculator.Models.Dis;
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


public class HTTPreq implements Runnable {
    // URL string for the HTTP request
    private String u;

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

            // Convert the buffer to a string and parse it to a JSON object
            if (buffer != null) {
                JSONObject json = new JSONObject(buffer.toString());
                // Get the rows array from the JSON object
                JSONArray array = json.getJSONArray("rows");
                // Get the first row object from the array
                JSONObject row = array.getJSONObject(0);
                // Get the elements array from the row object
                JSONArray ele = row.getJSONArray("elements");
                // Get the first element object from the array
                JSONObject dis = ele.getJSONObject(0);
                // Get the status from the element object
                String status = dis.getString("status");

                // Check if the status is "OK"
                if (status.equals("OK")) {
                    // Get the distance object from the element object
                    JSONObject distance = dis.getJSONObject("distance");
                    // Get the distance text from the distance object
                    String distanceString = distance.getString("text");

                    // Set the distance and update the Dis object flags
                    Dis.dis = distanceString;
                    Dis.lock = false;
                    Dis.valid_place = true;
                } else {
                    // If the status is not "OK", update the Dis object flags to indicate invalid place
                    Dis.lock = false;
                    Dis.valid_place = false;
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
}

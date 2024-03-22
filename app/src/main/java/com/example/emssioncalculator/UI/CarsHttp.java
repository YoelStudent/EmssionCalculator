package com.example.emssioncalculator.UI;

import static android.os.SystemClock.sleep;

import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.emssioncalculator.CalcHelper.Calc;
import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.Models.Car;

import org.checkerframework.checker.units.qual.C;
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
 * Use the {@link SearchCar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarsHttp implements Runnable
{
    private String u;
    public TextView tvdis;
    public static String distance_ret = "";
    public void SetString(String url){
        u = url;
    }

    public void use()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run()
    {

        try {
            String response = "";
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            URL url = new URL(u);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder buffer = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");

            }
            String message = org.apache.commons.io.IOUtils.toString(reader);
            //todo check if null
            if (buffer != null){
                JSONArray json = new JSONArray(buffer.toString());
                JSONObject jsonObject = json.getJSONObject(0);
                Integer mpg = Integer.parseInt(jsonObject.getString("city_mpg").toString());
                String fuelType =  jsonObject.getString("fuel_type");
                String make = jsonObject.getString("make").toString();
                String model = jsonObject.getString("model").toString();
                String year = jsonObject.getString("year").toString();

                FireBaseHelper  fireBaseHelper = new FireBaseHelper();

                fireBaseHelper.UpdateCar(new Car(make,model,year,mpg,fuelType));

            }




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public String Get_Distance()
    {
        return distance_ret;
    }


}

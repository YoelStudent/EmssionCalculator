package com.example.emssioncalculator.Http;

import static android.os.SystemClock.sleep;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.emssioncalculator.CalcHelper.Calc;
import com.example.emssioncalculator.Models.Car_Lock;
import com.example.emssioncalculator.Models.Dis;
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
public class HTTPreq implements Runnable
{
    private String u;
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


                JSONObject json = new JSONObject(buffer.toString());
                JSONArray array = json.getJSONArray("rows");
                JSONObject row = array.getJSONObject(0);
                JSONArray ele = row.getJSONArray("elements");
                JSONObject dis = ele.getJSONObject(0);
                String status = dis.getString("status");
                if (status.equals("OK")){

                    JSONObject distance = dis.getJSONObject("distance");
                    String distanceString = distance.getString("text");


                    Dis.dis = distanceString;
                    Dis.lock = false;
                    Dis.valid_place = true;
                }
                else
                {
                    Dis.lock = false;
                    Dis.valid_place =false;
                }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }



}

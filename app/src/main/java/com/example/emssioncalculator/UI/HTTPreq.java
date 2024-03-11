package com.example.emssioncalculator.UI;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link test#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HTTPreq implements Runnable
{
    private String u;
    public volatile String distance_ret = "";
    public void SetString(String url){
        u = url;
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
            JSONObject distance = dis.getJSONObject("distance");
            distance_ret=distance.getString("text");
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

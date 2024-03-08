package com.example.emssioncalculator.UI;

import android.util.Log;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }
            distance_ret = buffer.substring(0);
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray array = jsonObject.getJSONArray("routes");
//                    JSONObject routes = array.getJSONObject(0);
//                    JSONArray legs = routes.getJSONArray("legs");
//                    JSONObject steps = legs.getJSONObject(0);
//                    JSONObject distance = steps.getJSONObject("distance");
//                    parsedDistance=distance.getString("text");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String Get_Distance()
    {
        return distance_ret;
    }


}

package com.example.emssioncalculator.UI;

import static android.os.SystemClock.sleep;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.emssioncalculator.CalcHelper.Calc;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalcEmm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HTTPreq implements Runnable
{
    private String u;
    public TextView tvdis;
    public static String distance_ret = "";
    public void SetString(String url){
        u = url;
    }
    public static void SetDis(String s)
    {
        distance_ret =s;
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
            JSONObject distance = dis.getJSONObject("distance");
            String distanceString = distance.getString("text");
            //tvdis is null when not running in debugger mode, check whether its a problem with the logcat crash report saying shit about ther firestore
            //or probelms regarding the thread running in the background not getting the tvdis id proper prob the lat, there is no other good way to trasfer the data,
            // besides maybe sharedpref prob worth checking out
            Dis dis1 = new Dis();
            Dis.dis = distanceString;
            Dis.lock = false;
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

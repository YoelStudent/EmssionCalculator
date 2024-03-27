package com.example.emssioncalculator.CalcHelper;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.widget.TextView;

import com.example.emssioncalculator.Models.Car_Lock;
import com.example.emssioncalculator.Models.Dis;
import com.example.emssioncalculator.UI.CarsHttp;
import com.example.emssioncalculator.UI.HTTPreq;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
//TODO update page

public class Calc {
    public static String final_distance = "";
    public String getDistance(Location location, Place des, TextView textView) {
        String u = "  https://maps.googleapis.com/maps/api/distancematrix/json?destinations=" + des.getLatLng().latitude + "," + des.getLatLng().longitude + "&origins=" + location.getLatitude() +"," + location.getLongitude() + "&units=metric&travelmode=driving&key=";
        u += "AIzaSyCaYF1IVpeCYRV5H75bWNKE2JmgFOGFK1k";
        HTTPreq httPreq = new HTTPreq();
        httPreq.tvdis = textView;
        httPreq.SetString(u);
        httPreq.use();
        while (Dis.lock){
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Dis.lock = true;

        return final_distance;
    }
    public String getCar(String Car,String Model, String Year)
    {
        String u = "https://api.api-ninjas.com/v1/cars?limit=2&make=" + Car + "&model=" + Model + "&year=" + Year + "&X-Api-Key=";
        u += "JbRMmeIXk1MJts6VqxnQH08904D2zelSzoO3kMEY";
        CarsHttp httPreq = new CarsHttp();
        httPreq.SetString(u);
        httPreq.use();
        while (Car_Lock.flag){
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Car_Lock.flag = true;
        return final_distance;
    }
}

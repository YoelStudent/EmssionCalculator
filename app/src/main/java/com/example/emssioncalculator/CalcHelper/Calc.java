package com.example.emssioncalculator.CalcHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.widget.TextView;

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
    public static String getDistance(Location location, Place des, TextView textView) {
        String u = "  https://maps.googleapis.com/maps/api/distancematrix/json?destinations=" + des.getLatLng().latitude + "," + des.getLatLng().longitude + "&origins=" + location.getLatitude() +"," + location.getLongitude() + "&units=metric&travelmode=driving&key=";
        u += "AIzaSyCaYF1IVpeCYRV5H75bWNKE2JmgFOGFK1k";
        HTTPreq httPreq = new HTTPreq();
        httPreq.tvdis = textView;
        httPreq.SetString(u);
        httPreq.use();

        return final_distance;
    }
    public String getCar(String Car,TextView textView,String Model, String Year)
    {
        String u = "https://api.api-ninjas.com/v1/cars?limit=2&make=" + Car + "&model=" + Model + "&year=" + Year + "&X-Api-Key=";
        u += "JbRMmeIXk1MJts6VqxnQH08904D2zelSzoO3kMEY";
        CarsHttp httPreq = new CarsHttp();
        httPreq.tvdis = textView;
        httPreq.SetString(u);
        httPreq.use();

        String l = httPreq.Get_Distance();

        return final_distance;
    }
}

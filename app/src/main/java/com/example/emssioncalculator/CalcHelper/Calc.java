package com.example.emssioncalculator.CalcHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;

import com.example.emssioncalculator.UI.HTTPreq;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;


public class Calc {

    public String getDistance(Location location,Place des) {
        String u = "  https://maps.googleapis.com/maps/api/distancematrix/json?destinations=" + des.getLatLng().latitude + "," + des.getLatLng().longitude + "&origins=" + location.getLatitude() +"," + location.getLongitude() + "&units=metric&travelmode=driving&key=";
        u += "AIzaSyCaYF1IVpeCYRV5H75bWNKE2JmgFOGFK1k";
        HTTPreq httPreq = new HTTPreq();
        httPreq.SetString(u);
        Thread thread = new Thread(httPreq);
        thread.start();
        String l = httPreq.Get_Distance();
        return httPreq.Get_Distance();
    }
}

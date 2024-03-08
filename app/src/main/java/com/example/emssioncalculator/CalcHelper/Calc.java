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

    public String getDistance(Place origin, Place des) {

        String u = "https://maps.googleapis.com/maps/api/distancematrix/json?origin=" + origin.getAddress() + "&destination=" + des.getAddress() + "&sensor=false&units=metric&mode=drivingA&key=";
        u += "AIzaSyCaYF1IVpeCYRV5H75bWNKE2JmgFOGFK1k";
        HTTPreq httPreq = new HTTPreq();
        httPreq.SetString(u);
        Thread thread = new Thread(httPreq);
        thread.start();
        return httPreq.Get_Distance();
    }
}

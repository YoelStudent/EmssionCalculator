package com.example.emssioncalculator.CalcHelper;

import static java.lang.Thread.sleep;

import android.location.Location;

import com.example.emssioncalculator.Models.Car_Lock;
import com.example.emssioncalculator.Models.Dis;
import com.example.emssioncalculator.Http.CarsHttp;
import com.example.emssioncalculator.Http.HTTPreq;
import com.google.android.libraries.places.api.model.Place;
//TODO update page

public class Calc {
    public void getDistance(Location location, Place des) {
        String u = "  https://maps.googleapis.com/maps/api/distancematrix/json?destinations=" + des.getLatLng().latitude + "," + des.getLatLng().longitude + "&origins=" + location.getLatitude() +"," + location.getLongitude() + "&units=metric&travelmode=driving&key=";
        u += "AIzaSyCaYF1IVpeCYRV5H75bWNKE2JmgFOGFK1k";
        HTTPreq httPreq = new HTTPreq();
        httPreq.SetString(u);
        httPreq.use();
        int timer = 0;

        while (Dis.lock){
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            timer++;

            if (timer == 4)
            {
                Dis.valid_place = false;
                break;
            }
        }
        Dis.lock = true;

    }
    public void getCar(String Car,String Model, String Year)
    {
        String u = "https://api.api-ninjas.com/v1/cars?limit=2&make=" + Car + "&model=" + Model + "&year=" + Year + "&X-Api-Key=";
        u += "JbRMmeIXk1MJts6VqxnQH08904D2zelSzoO3kMEY";
        CarsHttp httPreq = new CarsHttp();
        httPreq.SetString(u);
        httPreq.use();
        int timer = 0;
        while (Car_Lock.flag){
            try {
                sleep(2000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            timer++;
            if (timer == 4)
            {
                Car_Lock.valid_car = false;
                break;
            }
        }
        Car_Lock.flag = true;
    }
}

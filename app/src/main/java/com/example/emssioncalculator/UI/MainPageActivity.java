package com.example.emssioncalculator.UI;

import static android.view.View.VISIBLE;

import static java.lang.Thread.sleep;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.emssioncalculator.CalcHelper.Calc;
import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.Models.Car;
import com.example.emssioncalculator.Models.Cur_User;
import com.example.emssioncalculator.Models.Dis;
import com.example.emssioncalculator.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MainPageActivity extends AppCompatActivity
{
    int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ImageButton btn_add;
    ImageButton btn_profile;
    ImageButton btn_history;
    PlacesClient placesClient;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        String Api_key = "AIzaSyAzIwZsPI9AmYBSXxt1edsHUjk9QJ8_xMo";
        btn_add = findViewById(R.id.btnCalculateRoute);
        btn_profile = findViewById(R.id.btnAccount);
        btn_history = findViewById(R.id.btnHistory);
        if (!Places.isInitialized()){
            Places.initialize(getApplicationContext(), Api_key);
        }
        placesClient = Places.createClient(this);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                if (fragmentManager.findFragmentById(R.id.fragment_container_view) != null)
                {
                    fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.fragment_container_view)).commit();

                }

                findViewById(R.id.autocomplete_fragment).setVisibility(VISIBLE);
                final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);



                autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,Place.Field.NAME));

                autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                    @Override
                    public void onError(@NonNull Status status) {
                    }

                    @Override
                    public void onPlaceSelected(@NonNull Place place) {
                        if (checkLocationPermission()) {
                            Calc calc = new Calc();

                            fusedLocationClient = LocationServices.getFusedLocationProviderClient(autocompleteSupportFragment.requireContext());
                            fusedLocationClient.getLastLocation()
                                    .addOnSuccessListener(autocompleteSupportFragment.requireActivity(), new OnSuccessListener<Location>() {
                                        PlacesClient placesClient;
                                        @Override
                                        public void onSuccess(Location location) {
                                            // Got last known location. In some rare situations this can be null.
                                            if (location != null) {
                                                calc.getDistance(location,place);
                                                if (Dis.valid_place)
                                                {
                                                    fragmentManager.beginTransaction()
                                                            .replace(R.id.fragment_container_view, CalcEmm.class, null)
                                                            .setReorderingAllowed(true)
                                                            .addToBackStack("name") // Name can be null
                                                            .commit();
                                                }
                                                else{
                                                    Toast.makeText(MainPageActivity.this, "invalid place", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        }
                                    });

                        }
                    }
                });



            }
        });
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.autocomplete_fragment).setVisibility(View.GONE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, ProfileFrag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();

            }
        });
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.autocomplete_fragment).setVisibility(View.GONE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, History_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();

            }
        });

    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                String title = "Location Settings";
                String text = "blablabla";
                new AlertDialog.Builder(this)
                        .setTitle(title)
                        .setMessage(text)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainPageActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainPageActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


}
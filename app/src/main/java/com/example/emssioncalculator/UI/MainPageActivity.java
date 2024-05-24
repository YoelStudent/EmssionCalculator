
package com.example.emssioncalculator.UI;

import static android.view.View.VISIBLE;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.emssioncalculator.CalcHelper.Calc;
import com.example.emssioncalculator.Models.Dis;
import com.example.emssioncalculator.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MainPageActivity extends AppCompatActivity {
    // Permission request code
    int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    // UI components
    ImageButton btn_add;
    ImageButton btn_info;
    ImageButton btn_profile;
    ImageButton btn_history;

    // Google Places client
    PlacesClient placesClient;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Initialize Google Places API
        String Api_key = "AIzaSyAzIwZsPI9AmYBSXxt1edsHUjk9QJ8_xMo";
        btn_add = findViewById(R.id.btnCalculateRoute);
        btn_profile = findViewById(R.id.btnAccount);
        btn_history = findViewById(R.id.btnHistory);
        btn_info = findViewById(R.id.btnInfo);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), Api_key);
        }
        placesClient = Places.createClient(this);

        // Hide the autocomplete fragment initially
        findViewById(R.id.autocomplete_fragment).setVisibility(View.INVISIBLE);

        // Set click listener for the "Add" button
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                // Remove existing fragment if present
                if (fragmentManager.findFragmentById(R.id.fragment_container_view) != null) {
                    fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.fragment_container_view)).commit();
                }

                // Show the autocomplete fragment
                findViewById(R.id.autocomplete_fragment).setVisibility(VISIBLE);
                final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

                // Set fields to be returned in the Place object
                autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));

                // Set listener for place selection
                autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                    @Override
                    public void onError(@NonNull Status status) {
                        // Handle error
                    }

                    @Override
                    public void onPlaceSelected(@NonNull Place place) {
                        // Check for location permission
                        if (checkLocationPermission()) {
                            Calc calc = new Calc();

                            // Get last known location
                            fusedLocationClient = LocationServices.getFusedLocationProviderClient(autocompleteSupportFragment.requireContext());
                            fusedLocationClient.getLastLocation()
                                    .addOnSuccessListener(autocompleteSupportFragment.requireActivity(), new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            if (location != null) {
                                                // Calculate distance and display results
                                                calc.getDistance(location, place);
                                                if (Dis.valid_place) {
                                                    fragmentManager.beginTransaction()
                                                            .replace(R.id.fragment_container_view, CalcEmm.class, null)
                                                            .setReorderingAllowed(true)
                                                            .addToBackStack("name") // Name can be null
                                                            .commit();
                                                } else {
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

        // Set click listener for the "Profile" button
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

        // Set click listener for the "History" button
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

        // Set click listener for the "Info" button
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.autocomplete_fragment).setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, InfoFrag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();
            }
        });
    }

    // Method to check location permission
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                String title = "Location Settings";
                String text = "Please enable us to use your location in order for the app to function";
                new AlertDialog.Builder(this)
                        .setTitle(title)
                        .setMessage(text)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Prompt the user once explanation has been shown

                                startActivity(
                                        new Intent(
                                                android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null)
                                        )
                                );
                            }
                        })
                        .create()
                        .show();

            return false;
        } else {
            return true;
        }
    }
}

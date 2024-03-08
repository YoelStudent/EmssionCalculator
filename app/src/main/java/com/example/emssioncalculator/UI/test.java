package com.example.emssioncalculator.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.emssioncalculator.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.net.PlacesClient;

public class test extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    GoogleMap map;
    private SearchView searchViewMap;
    String parsedDistance;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int distance;
    LinearLayout main;
    private FusedLocationProviderClient fusedLocationClient;

    View fag;
    public test() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewCalc.
     */
    // TODO: Rename and change types and number of parameters
    public static test newInstance(String param1, String param2) {
        test fragment = new test();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_calc, container, false);

        searchViewMap = view.findViewById(R.id.svMap);
        textView = view.findViewById(R.id.tvDis);
        return  view;



    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;





    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}
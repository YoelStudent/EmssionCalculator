package com.example.emssioncalculator.UI;

// Import necessary classes
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.emssioncalculator.DB.MyDatabaseHelper;
import com.example.emssioncalculator.Models.Cur_User;
import com.example.emssioncalculator.Models.Dis;
import com.example.emssioncalculator.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalcEmm extends Fragment {

    // Parameter arguments for fragment initialization
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    GoogleMap map;
    private SearchView searchViewMap;
    String parsedDistance;

    // Parameters
    private String mParam1;
    private String mParam2;
    private int distance;
    LinearLayout main;
    private FusedLocationProviderClient fusedLocationClient;

    View fag;

    // Required empty public constructor
    public CalcEmm() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewCalc.
     */
    // Factory method to create a new instance of this fragment
    public static CalcEmm newInstance(String param1, String param2) {
        CalcEmm fragment = new CalcEmm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve parameters if available
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // Declare UI components
    TextView tvTrees;
    TextView tvDis;
    Button btnSave;
    private TextView tvDistance;
    private View v;
    private TextView tvCar;

    // Method to show results (currently empty)
    public void Show_Res(View view) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_calc, container, false);

        // Initialize UI components
        tvDis = view.findViewById(R.id.tvDis);
        tvTrees = view.findViewById(R.id.tvTrees);
        btnSave = view.findViewById(R.id.btnSave);

        // Get the distance and car details
        String d = Dis.dis.substring(0, Dis.dis.length() - 3);
        double kpl = 0.425143707 * Cur_User.car.getMpg();  // Convert MPG to KPL
        double lpk = 1 / kpl;  // Calculate L/100km
        d = d.replace(",", "");  // Remove any commas in the distance string
        double fuel_con = lpk * Math.floor(Double.parseDouble(d));  // Calculate fuel consumption

        // Fuel type multipliers
        double Gasoline = 2.3;
        double Diesel = 2.7;
        double res = 0;

        // Calculate emissions based on fuel type
        if (Cur_User.car.getFuelType().equals("diesel")) {
            res = fuel_con * Diesel;
        } else {
            res = Gasoline * fuel_con;
        }

        // Round the result
        res = Math.floor(res);
        String result = String.valueOf(res);

        // Set the results in the TextViews
        tvDis.setText(result);
        String trees = String.valueOf(Math.round(res / 25));
        if (res / 25 < 1) {
            tvTrees.setText("1 tree");
        } else {
            tvTrees.setText(trees + " trees");
        }

        // Set click listener for the Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the emission data to the database
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(requireContext());
                Date d1 = new Date();

                // Format the date
                String pattern = "MM-dd-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                String s = d1.toString();
                s.replace("GMT", "");
                s = s.substring(0, s.length());

                // Add the data to the database
                myDatabaseHelper.addItem("0", result, trees, simpleDateFormat.format(d1), "");

                // Replace the current fragment with the History fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, History_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")  // Name can be null
                        .commit();
            }
        });
        return view;
    }
}

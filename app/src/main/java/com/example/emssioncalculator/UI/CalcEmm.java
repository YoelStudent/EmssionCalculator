package com.example.emssioncalculator.UI;

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

import java.util.Date;

public class CalcEmm extends Fragment {

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
    public CalcEmm() {
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


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    TextView tvTrees;
    TextView tvDis;
    Button btnSave;
    private TextView tvDistance;
    private  View v;
    private TextView tvCar;
    public void Show_Res(View view){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_calc, container, false);
        tvDis = view.findViewById(R.id.tvDis);
        tvTrees = view.findViewById(R.id.tvTrees);
        btnSave = view.findViewById(R.id.btnSave);
        String d = Dis.dis.substring(0, Dis.dis.length()-3);


        double kpl = 0.425143707 * Cur_User.car.getMpg();
        double lpk = 1 / kpl;
        d = d.replace(",","");
        double fuel_con = lpk * Math.floor(Double.parseDouble(d));
        double Gasoline = 2.3;
        double Diesel = 2.7;
        double res = 0;
        if (Cur_User.car.getFuelType().equals("diesel"))
        {
            res = fuel_con * Diesel;
        }
        else{
            res = Gasoline * fuel_con;
        }
        res = Math.floor(res);
        String result = String.valueOf(res);
        tvDis.setText(result);
        String trees = String.valueOf(Math.round(res/25));
        if (res/25 <1){
            tvTrees.setText("1 tree");
        }
        else{
            tvTrees.setText(trees + " trees");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(requireContext());
                Date d1 = new Date();
                String s = d1.toString();
                myDatabaseHelper.addItem("0",result,trees,s,"");
            }

        });
        return  view;



    }
}
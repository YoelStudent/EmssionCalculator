package com.example.emssioncalculator.UI;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.emssioncalculator.DB.MyDatabaseHelper;
import com.example.emssioncalculator.R;

import java.util.ArrayList;
import java.util.List;


public class History_Frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Button> buttons;
    public History_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment History_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static History_Frag newInstance(String param1, String param2) {
        History_Frag fragment = new History_Frag();
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
        buttons = new ArrayList<>();
    }
    TableLayout tableLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_history_, container, false);
        tableLayout = view.findViewById(R.id.tableLay);
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(requireContext());

        Cursor cursor = myDatabaseHelper.readAllData();
        if (cursor.moveToLast()) {
            do {
                // Retrieve data from the cursor
                String index = cursor.getString(1);
                String co2 = cursor.getString(2);
                String trees = cursor.getString(3);
                String date = cursor.getString(4);
                String name = cursor.getString(5);

                // Add a row to the table for each set of data
                addRowToTable(index, co2, trees, date, name);
            } while (cursor.moveToPrevious());
        }

        // Close the cursor and database
        cursor.close();
        myDatabaseHelper.close();
        return view;
    }
    private void addRowToTable(String index, String co2, String trees, String date, String name) {
        // Create a new row
        TableRow tableRow = new TableRow(requireContext());
        tableRow.setBackgroundResource(R.drawable.row_background);

        // Create TextViews for each column
        TextView textViewCO2 = createTextView(co2);
        TextView textViewTrees = createTextView(trees);

        TextView textViewDate = createTextView(date);

        TextView textViewName = createTextView(name);
        Button button = new Button(requireContext());
        button.setText("DELETE");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(requireContext());
                Cursor cursor = myDatabaseHelper.findIdByDate(date);
                cursor.moveToFirst();
                myDatabaseHelper.deleteOneRow(cursor.getString(0));
                tableRow.removeAllViews();
            }
        });
        buttons.add(button);

        // Add TextViews to the row
        //tableRow.addView(textViewIndex);
        tableRow.addView(textViewCO2);
        tableRow.addView(textViewTrees);
        tableRow.addView(textViewDate);
        tableRow.addView(textViewName);
        tableRow.addView(button);
        // Add the TableRow to the TableLayout
        tableLayout.addView(tableRow);
    }
    private TextView createTextView(String text) {
        TextView textView = new TextView(requireContext());
        textView.setText(text);
        textView.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(8, 8, 8, 8);

        textView.setTextColor(getResources().getColor(android.R.color.black)); // Set text color
        textView.setTextSize(16); // Set text size
        return textView;
    }

}
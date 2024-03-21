package com.example.emssioncalculator.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.Models.User;
import com.example.emssioncalculator.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFrag newInstance(String param1, String param2) {
        ProfileFrag fragment = new ProfileFrag();
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
    private void showProfileUpdateDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_alert, null);
        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextAddress = dialogView.findViewById(R.id.editTextAddress);
        EditText editTextName = dialogView.findViewById(R.id.editTextPass);

        dialogBuilder.setView(dialogView);


        dialogBuilder.setTitle("Update Profile");
        dialogBuilder.setMessage("Please fill in your information:");
        FireBaseHelper fireBaseHelper = new FireBaseHelper();
        fireBaseHelper.GetUser(new FireBaseHelper.IGetUser() {
            @Override
            public void OnGotUser(User user) {
                editTextName.setText(user.getName());
            }
        });
        dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do something with the profile information
                String name = editTextName.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                //fireBaseHelper.UpdateUser(new);
//                int age = Integer.parseInt(editTextAge.getText().toString().trim());

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancelled.
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    Button btnUp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnUp = view.findViewById(R.id.btnUp);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfileUpdateDialog();
            }
        });
        return view;

    }
}
package com.example.emssioncalculator.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emssioncalculator.CalcHelper.Calc;
import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.Models.Car_Lock;
import com.example.emssioncalculator.Models.Cur_User;
import com.example.emssioncalculator.Models.User;
import com.example.emssioncalculator.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFrag extends Fragment {

    // Arguments for fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Parameters
    private String mParam1;
    private String mParam2;

    // Required empty public constructor
    public ProfileFrag() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFrag.
     */
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

    // Method to show the profile update dialog
    private void showProfileUpdateDialog() {
        ProgressDialog pd = new ProgressDialog(requireContext());
        pd.setCancelable(false);
        pd.setTitle("");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_alert, null);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextAge = dialogView.findViewById(R.id.editTextAge);
        EditText editTextPass = dialogView.findViewById(R.id.editTextPass);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Update Profile");
        dialogBuilder.setMessage("Please fill in your information:");

        FireBaseHelper fireBaseHelper = new FireBaseHelper();
        fireBaseHelper.GetUser(new FireBaseHelper.IGetUser() {
            @Override
            public void OnGotUser(User user) {
                editTextName.setText(user.getName());
                editTextPass.setText(user.getPass());
                editTextAge.setText(user.getAge());
            }
        });

        dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Update profile information
                User user = new User(Cur_User.email, editTextName.getText().toString().trim(), editTextPass.getText().toString().trim(), editTextAge.getText().toString().trim());
                int err = user.Check_User();
                if (err == 0) {
                    fireBaseHelper.UpdateUser(user);
                } else if (err == 2) {
                    Toast.makeText(requireContext(), "invalid Name", Toast.LENGTH_SHORT).show();
                } else if (err == 3) {
                    Toast.makeText(requireContext(), "invalid Password", Toast.LENGTH_SHORT).show();
                } else if (err == 4) {
                    Toast.makeText(requireContext(), "invalid Age", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancelled
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    // Method to show the car search dialog
    private void showCarSearch() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.car_search_dialog, null);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextModel = dialogView.findViewById(R.id.editTextModel);
        EditText editTextYear = dialogView.findViewById(R.id.editTextYear);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Search Car");
        dialogBuilder.setMessage("Please fill in your information:");

        FireBaseHelper fireBaseHelper = new FireBaseHelper();
        editTextName.setText(Cur_User.car.getMake());
        editTextModel.setText(Cur_User.car.getModel());
        editTextYear.setText(Cur_User.car.getYear());

        dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Update car information
                Calc c = new Calc();
                c.getCar(editTextName.getText().toString(), editTextModel.getText().toString(), editTextYear.getText().toString());
                FireBaseHelper fireBaseHelper = new FireBaseHelper();

                if (Car_Lock.valid_car) {
                    fireBaseHelper.UpdateCar(Cur_User.car);
                    Toast.makeText(requireContext(), "valid car", Toast.LENGTH_SHORT).show();
                    editTextName.setText(Cur_User.car.getMake());
                    editTextModel.setText(Cur_User.car.getModel());
                    editTextYear.setText(Cur_User.car.getYear());
                } else {
                    Toast.makeText(requireContext(), "invalid car", Toast.LENGTH_SHORT).show();
                    editTextName.setText(Cur_User.car.getMake());
                    editTextModel.setText(Cur_User.car.getModel());
                    editTextYear.setText(Cur_User.car.getYear());
                }
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancelled
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    private void showDeleteDialog() {
        ProgressDialog pd = new ProgressDialog(requireContext());
        pd.setCancelable(false);
        pd.setTitle("Delete User");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_alert, null);


        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Delete Profile");
        dialogBuilder.setMessage("ARE YOU SURE YOU WANT TO DELETE PROFILE");

        FireBaseHelper fireBaseHelper = new FireBaseHelper();

        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Update profile information

                fireBaseHelper.DeleteUser(requireContext());
            }
        });

        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancelled
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    // UI components
    Button btnUp;
    Button btnCar;
    Button btnDel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize buttons
        btnUp = view.findViewById(R.id.btnUp);
        btnCar = view.findViewById(R.id.btnCarSearch);
        btnDel = view.findViewById(R.id.btnDelete);

        // Set click listener for "Car Search" button
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCarSearch();
            }
        });

        // Set click listener for "Update Profile" button
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfileUpdateDialog();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        return view;
    }
}

package com.example.emssioncalculator.SignUp;
import com.example.emssioncalculator.CalcHelper.Calc;
import com.example.emssioncalculator.DB.FireBaseHelper;
import com.example.emssioncalculator.LogIn.MainActivity;
import com.example.emssioncalculator.Models.Car;
import com.example.emssioncalculator.Models.Car_Lock;
import com.example.emssioncalculator.Models.Cur_User;
import com.example.emssioncalculator.repository.repository;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emssioncalculator.R;
import com.example.emssioncalculator.Models.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignUpActivity extends AppCompatActivity {
    private TextView tvSignIn;
    private Button btnSignUp;
    private EditText edEmail, edName, edPass, edBirthDate, edCarMake,edCarModel, edCarYear;

    private repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        edEmail = findViewById(R.id.editTextEmail);
        edName = findViewById(R.id.editTextFullName);
        edPass = findViewById(R.id.editTextNewPassword);
        edBirthDate = findViewById(R.id.editTextBirthDate);
        edCarMake = findViewById(R.id.editTextCarMake);
        edCarModel = findViewById(R.id.editTextCarModel);
        edCarYear = findViewById(R.id.editTextCarYear);

        tvSignIn = findViewById(R.id.tvSignIn);

        btnSignUp = findViewById(R.id.btnSignUp);

        repository = new repository(this);
        Context c = this;
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User u = new User(edEmail.getText().toString(),edName.getText().toString(),edPass.getText().toString(), edBirthDate.getText().toString());
                Calc calc = new Calc();

                SignUp s = new SignUp(u,c);
                calc.getCar(edCarMake.getText().toString(), edCarModel.getText().toString(), edCarYear.getText().toString());
                if (!Car_Lock.valid_car){
                    Toast.makeText(c, "invalid car", Toast.LENGTH_SHORT).show();
                }
                else {

                    if(s.Check_User()==0)
                    {
                        Cur_User.email = u.getEmail();

                        repository.Add_User(u, new FireBaseHelper.IAddCar() {
                            @Override
                            public void OnAddCar() {
                                Toast.makeText(c, "user added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });


                    }
                }
            }
        });
    }
}
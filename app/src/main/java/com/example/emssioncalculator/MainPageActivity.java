package com.example.emssioncalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainPageActivity extends AppCompatActivity
{
    ImageButton btn_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        btn_add = findViewById(R.id.btnCalculateRoute);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, NewCalc.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();
            }
        });

    }
}
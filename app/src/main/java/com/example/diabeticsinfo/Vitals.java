package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Vitals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);

    }

    public void goToBloodSugarLevel(View view) {
        Intent intent = new Intent(this, BSL.class);
        startActivity(intent);
    }

    public void goToBloodPressure(View view) {
        Intent intent = new Intent(this, BloodPressure.class);
        startActivity(intent);
    }

}

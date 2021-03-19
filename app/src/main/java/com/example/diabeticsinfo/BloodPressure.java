package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class BloodPressure extends AppCompatActivity {
    private Object Menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.activity_blood_pressure, menu);
        return true;
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, Vitals.class);
        startActivity(intent);
    }
}
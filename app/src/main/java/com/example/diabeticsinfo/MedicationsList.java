package com.example.diabeticsinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MedicationsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications_list);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, Medications.class);
        startActivity(intent);
    }
}

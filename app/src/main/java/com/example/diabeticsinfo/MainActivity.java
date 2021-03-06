package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
//Comment 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void goToMedications(View view) {
        Intent intent = new Intent(this, Medications.class);
        startActivity(intent);
    }

    public void goToExercise (View view) {
        Intent intent = new Intent(this, Exercise.class);
        startActivity(intent);
    }

    public void goToGenerateReport(View view) {
        Intent intent = new Intent(this, GenerateReportActivity.class);
        startActivity(intent);
    }
    //Hi From Matt!
    //Hello Matt I'm nate
    //Hello from Jaden
    //Edit!
}
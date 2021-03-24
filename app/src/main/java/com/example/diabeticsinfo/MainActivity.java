package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.gson.Gson;
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

    /***
     * @author Nathan Hoskins
     * Because of newer versions of Android Notification methods needed to be updated.
     * The newer system will only activate on newer android versions.
     */

    public void goToVitals(View view) {
        Intent intent = new Intent(this, Vitals.class);
        startActivity(intent);
    }

    public void goToMedicationActivity(View view){
        Intent intent = new Intent(this, MedicationActivity.class);
        startActivity(intent);
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
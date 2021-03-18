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
    public static final String CHANNEL_1_ID = "med_note";
    public static final String CHANNEL_2_ID = "vital_note";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotficationChannels();
    }

    /***
     * @author Nathan Hoskins
     * Because of newer versions of Android Notification methods needed to be updated.
     * The newer system will only activate on newer android versions.
     */
    private void createNotficationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel med_Channel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Diabetes Medicine",
                    NotificationManager.IMPORTANCE_HIGH
            );

            med_Channel.setDescription("A Channel for Medicine Notifications");

            NotificationChannel vital_Channel = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Vital Readings",
                    NotificationManager.IMPORTANCE_HIGH
            );
            
            vital_Channel.setDescription("A Channel for Medicine Notifications");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(med_Channel);
            manager.createNotificationChannel(vital_Channel);
        }
    }
    public void goToVitals(View view) {
        Intent intent = new Intent(this, Vitals.class);
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
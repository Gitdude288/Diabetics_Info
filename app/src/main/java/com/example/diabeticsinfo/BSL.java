package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class BSL extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_l_s);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.activity_b_l_s, menu);
        return true;
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, Vitals.class);
        startActivity(intent);
    }
    /***
     * This is the Vitals alert constructor.
     * @param v the View screen of the device
     * @param <title> is the title of the notification
     * @param <message> is the message to appear in notifications
     * @author Nate Hoskins
     *
     */
    public <title, message> void vitalTimeAlert(View v, String title, String message) {
        Notification notification = new NotificationCompat.Builder(this, MainActivity.CHANNEL_2_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_time_not)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

    }
    public <title, message> void vitalWarningAlert(View v, String title, String message){
        Notification notification = new NotificationCompat.Builder(this, MainActivity.CHANNEL_2_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_warning_not)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

}
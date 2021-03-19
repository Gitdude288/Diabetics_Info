package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BSL extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_l_s);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, Vitals.class);
        startActivity(intent);
    }
    /***
     * This is the Vitals alert constructor.
     * @param v the View screen of the device
     * @param <title> is the title of the notification
     * @param <message> is the message to appear in notification
     * @param <time> is a boolean, for if the message is based on a time like taking morning meds
     * @author Nate Hoskins
     *
     */
    public <title, message, time> void vitalAlert(View v, String title, String message, Boolean time){

        Notification notification = new NotificationCompat.Builder(this, MainActivity.CHANNEL_2_ID)
                .setContentTitle(title)
                .setContentText(message);
        if(time){
            ((NotificationCompat.Builder) notification).setSmallIcon(R.drawable.ic_time_not);
        }
        else{
            ((NotificationCompat.Builder) notification).setSmallIcon(R.drawable.ic_warning_not);
        }
    }

}
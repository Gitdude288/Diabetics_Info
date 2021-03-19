package com.example.diabeticsinfo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.app.Application;

public class App extends Application{
    public static final String CHANNEL_1_ID = "med_note";
    public static final String CHANNEL_2_ID = "vital_note";
    @Override
    public  void onCreate(){
        super.onCreate();
        createChannels();
    }
    private void createChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel high_Channel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Diabetes Medicine",
                    NotificationManager.IMPORTANCE_HIGH
            );

            high_Channel.setDescription("A Channel for Medicine Notifications");

            NotificationChannel low_Channel = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Vital Readings",
                    NotificationManager.IMPORTANCE_LOW
            );

            low_Channel.setDescription("A Channel for Medicine Notifications");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(high_Channel);
            manager.createNotificationChannel(low_Channel);
        }
    }



    }
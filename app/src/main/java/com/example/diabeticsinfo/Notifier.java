package com.example.diabeticsinfo;

import android.app.Notification;
import android.content.Context;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.diabeticsinfo.App.CHANNEL_1_ID;
import static com.example.diabeticsinfo.App.CHANNEL_2_ID;

public class Notifier {
    private NotificationManagerCompat notificationManager;

    public Notifier(NotificationManagerCompat notificationManager) {
        this.notificationManager = notificationManager;
    }

    public <title, text, id> void sendOnHigh(View v, String title, String text, int id){
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_warning_not)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        notificationManager.notify(id, notification);
    }
    public <title, text, id> void sendOnLow(View v, String title, String text, int id){
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_time_not)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManager.notify(id, notification);
    }
}

package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class BSL extends AppCompatActivity {

    public BSLMeasurement bl = new BSLMeasurement();
    public String fileName = "bsl.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_l_s);

        try {
            bl = getBSLData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listBSL();
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.activity_b_l_s, menu);
        return true;
    }*/

    public void goBack(View view) {
        Intent intent = new Intent(this, Vitals.class);
        startActivity(intent);

        try {
            bl = getBSLData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listBSL();
    }
    /***
     * This is the Vitals alert constructor.
     * @param v the View screen of the device
     * @param <title> is the title of the notification
     * @param <message> is the message to appear in notifications
     * @author Nate Hoskins
     *
     */
    /*public <title, message> void vitalTimeAlert(View v, String title, String message) {
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
    }*/

    public void addBSL(View view) throws IOException {
        //Get medication name and amount that user entered
        //EditText editText = (EditText) findViewById(R.id.textView5);
        EditText editText2 = (EditText) findViewById(R.id.blsReading);
        Date date = new Date(2020, 12, 31);
        String min = editText2.getText().toString();
        int amt = -1;
        if (min != null){
            amt = Integer.parseInt(editText2.getText().toString());
        }

        if (date != null && amt > -1 ){
            bl.addDate(date);
            bl.addBSL(amt);
            Gson g = new Gson();
            String json = g.toJson(bl);


            FileOutputStream file = null;
            file = openFileOutput(fileName, MODE_PRIVATE);
            file.write(json.getBytes());
            file.close();

            /*sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MEDNAME, med);
            editor.putInt(MEDAMOUNT, amt);
            editor.apply();*/
            Log.i("addBSL","successfully added your BSL");
        }else {
            Log.e("addBSL","invalid input, did not add BSL");
        }

    }

    public BSLMeasurement getBSLData() throws IOException {
        File logFile = new File(fileName);
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        FileInputStream file = null;
        file = openFileInput(fileName);

        InputStreamReader isr = new InputStreamReader(file);
        BufferedReader bfr = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();

        String reader;

        while ((reader = bfr.readLine()) != null) {
            sb.append(reader).append("");
        }

        reader = sb.toString();

        Gson gson = new Gson();
        bl = gson.fromJson(reader, BSLMeasurement.class);

        Log.i("addMedication", bl.getData());
        file.close();

        return bl;
    }

    public void listBSL() {
        TextView dateList = (TextView)findViewById(R.id.textView15);
        TextView minuteList = (TextView)findViewById(R.id.textView17);
        String d = "";
        String m = "";
        for (int i = 0; i < bl.dates.size(); i++) {
            d += bl.dates.get(i) + "\n";
            m += bl.bsl.get(i) + "\n";
        }
        dateList.setText(d);
        minuteList.setText(m);
    }
}
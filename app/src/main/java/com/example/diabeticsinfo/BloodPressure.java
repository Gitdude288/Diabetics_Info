package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.sql.Date;

public class BloodPressure extends AppCompatActivity {

    private Object Menu;
    public BloodPressureMeasurement bp = new BloodPressureMeasurement();
    public String fileName = "bloodpressureTest.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        try {
            bp = getBloodPressureData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listBloodPressure();
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

    public void addBloodPressure(View view) throws IOException {
        //Get medication name and amount that user entered
        EditText editText = (EditText) findViewById(R.id.sInput);
        EditText editText2 = (EditText) findViewById(R.id.dInput);

        EditText month = (EditText) findViewById(R.id.month4);
        EditText day = (EditText) findViewById(R.id.day4);
        EditText year = (EditText) findViewById(R.id.year4);

        int m = Integer.parseInt(month.getText().toString());
        int d = Integer.parseInt(day.getText().toString());
        int y = Integer.parseInt(year.getText().toString());
        java.sql.Date date = new Date(y - 1900, m - 1, d);

        String max = editText.getText().toString();
        String min = editText2.getText().toString();
        int amt = -1;
        int high = 0;
        if (min != null){
            amt = Integer.parseInt(editText2.getText().toString());
            high = Integer.parseInt(editText.getText().toString());
        }

        if (date != null && amt > -1 ){
            bp.addDate(date);
            bp.addBP(high, amt);
            Gson g = new Gson();
            String json = g.toJson(bp);


            FileOutputStream file = null;
            file = openFileOutput(fileName, MODE_PRIVATE);
            file.write(json.getBytes());
            file.close();

            /*sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MEDNAME, med);
            editor.putInt(MEDAMOUNT, amt);
            editor.apply();*/
            Log.i("addBloodPressure","successfully added your Blood Pressure");
        }else {
            Log.e("addBloodPressure","invalid input, did not add Blood Pressure");
        }

    }

    public BloodPressureMeasurement getBloodPressureData() throws IOException {
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
        bp = gson.fromJson(reader, BloodPressureMeasurement.class);

        Log.i("addBloodPressure", bp.getData());
        file.close();

        return bp;
    }

    public void listBloodPressure() {
        TextView dateList = (TextView)findViewById(R.id.textView18);
        TextView highList = (TextView)findViewById(R.id.textView20);
        TextView lowList = (TextView)findViewById(R.id.textView19);
        String d = "";
        String m = "";
        String l = "";
        for (int i = 0; i < bp.dates.size(); i++) {
            d += bp.dates.get(i) + "\n";
            m += bp.bpHigh.get(i) + "\n";
            l += bp.bpLow.get(i) + "\n";
        }
        dateList.setText(d);
        highList.setText(m);
        lowList.setText(l);
    }
}
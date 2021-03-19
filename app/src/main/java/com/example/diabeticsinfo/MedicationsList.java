package com.example.diabeticsinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MedicationsList extends AppCompatActivity {

    public Medicine m = new Medicine();
    public String fileName = "medicine.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications_list);

        try {
            m = getMedications();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listMeds();
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, Medications.class);
        startActivity(intent);
    }

    public Medicine getMedications() throws IOException {
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
        m = gson.fromJson(reader, Medicine.class);

        Log.i("addMedication", m.getMeds());
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return m;
    }

    public void listMeds() {
        TextView medList = (TextView)findViewById(R.id.textView3);
        TextView doseList = (TextView)findViewById(R.id.textView4);
        String meds = "";
        String doses = "";
        for (int i = 0; i < m.medicine.size(); i++) {
            meds += m.medicine.get(i) + "\n";
            doses += m.doses.get(i) + "\n";
        }
        medList.setText(meds);
        doseList.setText(doses);
    }
}

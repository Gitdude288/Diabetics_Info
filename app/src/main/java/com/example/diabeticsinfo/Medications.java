package com.example.diabeticsinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Medications extends AppCompatActivity {

    public Medicine m = new Medicine();
    public String fileName = "medicine.txt";
    //SharedPreferences sharedpreferences;
    //public static final String MyPREFERENCES = "Medications" ;
    //public static final String MEDNAME = "med";
    //public static final String MEDAMOUNT = "amt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        /*Map<String, Integer> medsList;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String med = sharedpreferences.getString(MEDNAME, "bob");
        int amt = sharedpreferences.getInt(MEDAMOUNT, 0);

        Log.d("onCreate: ","this is med name: " + med +" and this is amount: " + amt);*/

        try {
            m = getMedications();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //public void

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToList(View view) {
        Intent intent = new Intent(this, MedicationsList.class);
        startActivity(intent);
    }

    public void addMedication(View view) throws IOException {
        //Get medication name and amount that user entered
        EditText editText = (EditText) findViewById(R.id.medicationName);
        EditText editText2 = (EditText) findViewById(R.id.amount);
        String med = editText.getText().toString();
        String amtText = editText2.getText().toString();
        int amt = 0;
        if (amtText != null){
            amt = Integer.parseInt(editText2.getText().toString());
        }

        if (med != null && amt > 0 ){
            m.addMedication(med);
            m.addDose(amt);
            Gson g = new Gson();
            String json = g.toJson(m);


            FileOutputStream file = null;
            file = openFileOutput(fileName, MODE_PRIVATE);
            file.write(json.getBytes());
            file.close();

            /*sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MEDNAME, med);
            editor.putInt(MEDAMOUNT, amt);
            editor.apply();*/
            Log.i("addMedication","successfully added your medication");
        }else {
            Log.e("addMedication","invalid input, did not add medication");
        }

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
}

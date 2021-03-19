package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exercise extends AppCompatActivity {

    public ExerciseData ed = new ExerciseData();
    public String fileName = "exercise.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        try {
            ed = getExerciseData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listExercise();
    }
    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addExercise(View view) throws IOException {
        //Get medication name and amount that user entered
        EditText editText = (EditText) findViewById(R.id.textView5);
        EditText editText2 = (EditText) findViewById(R.id.textView6);
        String date = editText.getText().toString();
        String min = editText2.getText().toString();
        int amt = 0;
        if (min != null){
            amt = Integer.parseInt(editText2.getText().toString());
        }

        if (date != null && amt > 0 ){
            ed.addDate(date);
            ed.addMinutes(amt);
            Gson g = new Gson();
            String json = g.toJson(ed);


            FileOutputStream file = null;
            file = openFileOutput(fileName, MODE_PRIVATE);
            file.write(json.getBytes());
            file.close();

            /*sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MEDNAME, med);
            editor.putInt(MEDAMOUNT, amt);
            editor.apply();*/
            Log.i("addExercise","successfully added your exercise");
        }else {
            Log.e("addExercise","invalid input, did not add exercise");
        }

    }

    public ExerciseData getExerciseData() throws IOException {
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
        ed = gson.fromJson(reader, ExerciseData.class);

        Log.i("addMedication", ed.getData());
        file.close();

        return ed;
    }

    public void listExercise() {
        TextView dateList = (TextView)findViewById(R.id.dates);
        TextView minuteList = (TextView)findViewById(R.id.Minutes);
        String d = "";
        String m = "";
        for (int i = 0; i < ed.dates.size(); i++) {
            d += ed.dates.get(i) + "\n";
            m += ed.minutes.get(i) + "\n";
        }
        dateList.setText(d);
        minuteList.setText(m);
    }
}
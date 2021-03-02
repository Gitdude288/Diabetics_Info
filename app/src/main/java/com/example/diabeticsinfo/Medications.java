package com.example.diabeticsinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class Medications extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Medications" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        Map<String, Integer> medsList;
        
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addMedication(View view) {
        //Get medication name and amount that user entered
        EditText editText = (EditText) findViewById(R.id.medicationName);
        EditText editText2 = (EditText) findViewById(R.id.amount);
        String med = editText.getText().toString();
        int amt = Integer.parseInt(editText2.getText().toString());

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(med, amt);
        editor.apply();
    }

    public void getMedications(View view) {

    }
}

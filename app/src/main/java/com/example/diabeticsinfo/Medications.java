package com.example.diabeticsinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class Medications extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Medications" ;
    public static final String MEDNAME = "med";
    public static final String MEDAMOUNT = "amt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        Map<String, Integer> medsList;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String med = sharedpreferences.getString(MEDNAME, "bob");
        int amt = sharedpreferences.getInt(MEDAMOUNT, 0);

        Log.d("onCreate: ","this is med name: " + med +" and this is amount: " + amt);

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

    public void addMedication(View view) {
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
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MEDNAME, med);
            editor.putInt(MEDAMOUNT, amt);
            editor.apply();
            Log.i("addMedication","successfully added your medication");
        }else {
            Log.e("addMedication","invalid input, did not add medication");
        }

    }

    public void getMedications(View view) {

    }
}

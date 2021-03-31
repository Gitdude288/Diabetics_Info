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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MedicationListActivity extends AppCompatActivity {

    TextView medicationList;
    public MedicationPrescriptionGeneralHandler medHandler = new MedicationPrescriptionGeneralHandler();
    public String fileName = "medHandler.txt";
    private SaveLoad _saveLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_list);

        medicationList = findViewById(R.id.medicationList);
        _saveLoad = new SaveLoad(this);

        try {
            medHandler = _saveLoad.loadMedicationList();
        } catch (Exception e) {
            medicationList.setText(e.getMessage());
        }
        listMeds();
    }

    public void listMeds() {
        List<SingleMedicationPrescriptionHandler> medList = new ArrayList<>();
        medList = medHandler.cloneAll();
        String list = new String();

        for(SingleMedicationPrescriptionHandler handler: medList){
            list += handler.getPrescriptionName() + " " + handler.getMilligramDosageInASingleTablet() + "mg " + "has " + handler.getPillsRemainingInBottle() + " pill(s) left";
        }

        medicationList.setText(list);
    }

}
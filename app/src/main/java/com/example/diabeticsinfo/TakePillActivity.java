package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDateTime;

public class TakePillActivity extends AppCompatActivity {
    String _prescriptionName;
    int _prescriptionDosage;
    MedicationPrescriptionGeneralHandler _medHandler;
    TextView takeMedication;
    EditText editTakeMonth;
    EditText editTakeDay;
    EditText editTakeYear;
    EditText editTakeMilitaryHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pill);
        _prescriptionName = getIntent().getStringExtra("prescriptionName");
        _prescriptionDosage = getIntent().getIntExtra("prescriptionDosage", 0);

        takeMedication = findViewById(R.id.takeMedication);
        takeMedication.setText(_prescriptionName + " " + _prescriptionDosage + "mg");

        SaveLoad saveLoad = new SaveLoad(this);
        _medHandler = new MedicationPrescriptionGeneralHandler();

        try {
            _medHandler = saveLoad.loadMedicationList();
        } catch (Exception e) {

        }

        LocalDateTime now = LocalDateTime.now();

        String takeMM = String.valueOf(now.getMonthValue());
        String takeDD = String.valueOf(now.getDayOfMonth());
        String takeYYYY = String.valueOf(now.getYear());
        String takeHH = String.valueOf(now.getHour());

        editTakeMonth = findViewById(R.id.takeMonth);
        editTakeDay = findViewById(R.id.takeDay);
        editTakeYear = findViewById(R.id.takeYear);
        editTakeMilitaryHour = findViewById(R.id.takeMilitaryHour);

        editTakeMonth.setText(takeMM);
        editTakeDay.setText(takeDD);
        editTakeYear.setText(takeYYYY);
        editTakeMilitaryHour.setText(takeHH);

    }

    public void takeDose(View view){
        LocalDateTime timeTakePill;
        SingleMedicationPrescriptionHandler medicationBeingTaken = _medHandler.cloneFromList(_prescriptionName, _prescriptionDosage);

        int year = -1;
        int month = -1;
        int day = -1;
        int hour = -1;

        String yearText = editTakeYear.getText().toString();
        String monthText = editTakeMonth.getText().toString();
        String dayText = editTakeDay.getText().toString();
        String hourText = editTakeMilitaryHour.getText().toString();

        if(!yearText.isEmpty()){
            year = Integer.parseInt(yearText);
        }

        if(!monthText.isEmpty()){
            month = Integer.parseInt(monthText);
        }

        if(!dayText.isEmpty()){
            day = Integer.parseInt(dayText);
        }

        if(!hourText.isEmpty()){
            hour = Integer.parseInt(hourText);
        }

        try{
            timeTakePill = LocalDateTime.of(year, month, day, hour, 0);

            if(medicationBeingTaken.takePill(timeTakePill)){
                _medHandler.replace(_prescriptionName, _prescriptionDosage, medicationBeingTaken);
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
                SaveLoad saveLoad = new SaveLoad(this);
                try{
                    saveLoad.saveMedicationList(_medHandler);
                }catch(Exception e){

                }
            } else if(medicationBeingTaken.getPillsRemainingInBottle() > 0){
                Toast.makeText(this, "Already took medication at that date and time", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "Didn't work! Ran out of pills!", Toast.LENGTH_SHORT).show();
            }


        } catch(Exception e){
            Toast.makeText(this, "Invalid date/time", Toast.LENGTH_SHORT).show();
        }
    }
}
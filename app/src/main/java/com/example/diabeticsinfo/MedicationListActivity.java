package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicationListActivity extends AppCompatActivity {

    TextView medicationList;
    EditText takeDoseName;
    EditText editName;
    EditText removeName;
    EditText refillName;

    EditText takeDoseMG;
    EditText editMG;
    EditText removeMG;
    EditText refillMG;

    public MedicationPrescriptionGeneralHandler _medHandler;
    private SaveLoad _saveLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_list);

        medicationList = findViewById(R.id.medicationList);

        takeDoseName = findViewById(R.id.takeDoseName);
        editName = findViewById(R.id.editName);
        removeName = findViewById(R.id.removeName);
        refillName = findViewById(R.id.refillName);

        takeDoseMG = findViewById(R.id.takeDoseMG);
        editMG = findViewById(R.id.editMG);
        removeMG = findViewById(R.id.removeMG);
        refillMG = findViewById(R.id.refillMG);

        _saveLoad = new SaveLoad(this);

        _medHandler = new MedicationPrescriptionGeneralHandler();

        try {
            _medHandler = _saveLoad.loadMedicationList();
        } catch (Exception e) {
            medicationList.setText(e.getMessage());
        }
        listMeds();
    }

    public void listMeds() {
        List<SingleMedicationPrescriptionHandler> medList = new ArrayList<>();
        medList = _medHandler.cloneAll();
        String list = new String();

        for(SingleMedicationPrescriptionHandler handler: medList){
            list += handler.getPrescriptionName() + " " + handler.getMilligramDosageInASingleTablet() + "mg " + "has " + handler.getPillsRemainingInBottle() + " pill(s) left\n";
        }

        medicationList.setText(list);
    }

    public void takePill(View view){

        String prescriptionName = takeDoseName.getText().toString();
        String prescriptionDosageText = takeDoseMG.getText().toString();
        int prescriptionDosage = 0;
        if(!prescriptionDosageText.isEmpty()){
            prescriptionDosage = Integer.parseInt(prescriptionDosageText);
        }

        SingleMedicationPrescriptionHandler medicationBeingTaken = _medHandler.cloneFromList(prescriptionName, prescriptionDosage);
        if(medicationBeingTaken != null){
            Intent intent = new Intent(this, TakePillActivity.class);
            intent.putExtra("prescriptionName", medicationBeingTaken.getPrescriptionName());
            intent.putExtra("prescriptionDosage", medicationBeingTaken.getMilligramDosageInASingleTablet());
            startActivity(intent);
        }

    }

    public void delete(View view){
        int dosage = -1;
        String dosageText = removeMG.getText().toString();
        if(!dosageText.isEmpty()){
            dosage = Integer.parseInt(dosageText);
        }

        _medHandler.delete(removeName.getText().toString(), dosage);
        try{
            _saveLoad.saveMedicationList(_medHandler);
        } catch(Exception e){

        }
        listMeds();

    }

    public void refill(View view){
        int dosage = -1;
        String dosageText = refillMG.getText().toString();
        if(!dosageText.isEmpty()){
            dosage = Integer.parseInt(dosageText);
        }

        _medHandler.refillBottle(refillName.getText().toString(), dosage);
        try{
            _saveLoad.saveMedicationList(_medHandler);
        } catch(Exception e){

        }
        listMeds();
    }

}
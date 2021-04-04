package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

/**
 * @author Jaden Myers
 *
 */
public class EditMedicationActivity extends AppCompatActivity {

    MedicationPrescriptionGeneralHandler _generalHandler;
    String _prescriptionName;
    int _prescriptionDosage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medication);

        _prescriptionName = getIntent().getStringExtra("prescriptionName");
        _prescriptionDosage = getIntent().getIntExtra("prescriptionDosage", 0);

        _generalHandler = new MedicationPrescriptionGeneralHandler();
        SingleMedicationPrescriptionHandler editHandler = new SingleMedicationPrescriptionHandler();

        try {
            SaveLoad saveLoad = new SaveLoad(this);
            _generalHandler = saveLoad.loadMedicationList();
            editHandler = _generalHandler.cloneFromList(_prescriptionName, _prescriptionDosage);
        }catch (Exception e){

        }

        EditText editMedicationName = findViewById(R.id.medicationName2);
        EditText editDosage = findViewById(R.id.dosage2);
        EditText editNumTablet = findViewById(R.id.numTablet2);
        EditText editTimesTakenADay = findViewById(R.id.timesTakenADay2);
        EditText editRefillsLeft = findViewById(R.id.refillsLeft2);
        EditText editMaxPillCount = findViewById(R.id.maxPillCount2);
        EditText editCurrentPillCount = findViewById(R.id.currentPillCount2);
        EditText editMonth = findViewById(R.id.month2);
        EditText editDay = findViewById(R.id.day2);
        EditText editYear = findViewById(R.id.year2);

        int dosage = editHandler.getMilligramDosageInASingleTablet();
        String dosageText = String.valueOf(dosage);
        int numTablet = editHandler.getTakeThisManyTabletsAtaTime();
        String numTabletText = String.valueOf(numTablet);
        int timesTakenADay = editHandler.getTakeMedicationThisManyTimesADay();
        String timesTakenADayText = String.valueOf(timesTakenADay);
        int refillsLeft = editHandler.getRefillsRemaining();
        String refillsLeftText = String.valueOf(refillsLeft);
        int maxPill = editHandler.getMaxPillCountInBottle();
        String maxPillText = String.valueOf(maxPill);
        int currentPill = editHandler.getPillsRemainingInBottle();
        String currentPillText = String.valueOf(currentPill);
        int month = editHandler.getPrescriptionExpiration().getMonthValue();
        String monthText = String.valueOf(month);
        int day = editHandler.getPrescriptionExpiration().getDayOfMonth();
        String dayText = String.valueOf(day);
        int year = editHandler.getPrescriptionExpiration().getYear();
        String yearText = String.valueOf(year);

        editMedicationName.setText(editHandler.getPrescriptionName());
        editDosage.setText(dosageText);
        editNumTablet.setText(numTabletText);
        editTimesTakenADay.setText(timesTakenADayText);
        editRefillsLeft.setText(refillsLeftText);
        editMaxPillCount.setText(maxPillText);
        editCurrentPillCount.setText(currentPillText);
        editMonth.setText(monthText);
        editDay.setText(dayText);
        editYear.setText(yearText);
    }

    public void editMedication(View view) throws IOException {

        EditMedication editMedication = new EditMedication(this, _generalHandler, _prescriptionName, _prescriptionDosage);
        Thread thread = new Thread(editMedication);
        thread.start();

        //At the end of AddMedication.run() is where the medication list gets updated by saving the
        // MedicationPrescriptionGeneralHandler
        //Once inside AddMedication, just push ctrl F and then type "save" and you will skip right to it

    }
}
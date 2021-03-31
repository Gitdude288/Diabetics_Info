package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

/**
 * @author Jaden Myers
 *
 */
public class MedicationActivity extends AppCompatActivity {

    MedicationPrescriptionGeneralHandler _generalHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        _generalHandler = new MedicationPrescriptionGeneralHandler();

        try {
            SaveLoad saveLoad = new SaveLoad(this);
            _generalHandler = saveLoad.loadMedicationList();
        }catch (Exception e){

        }


    }

    public void goToList(View view) {
        Intent intent = new Intent(this, MedicationListActivity.class);
        startActivity(intent);
    }

    public void addMedication(View view) throws IOException {

        AddMedication addMedication = new AddMedication(this, _generalHandler);
        Thread thread = new Thread(addMedication);
        thread.start();

        //At the end of AddMedication.run() is where the medication list gets updated by saving the
        // MedicationPrescriptionGeneralHandler
        //Once inside AddMedication, just push ctrl F and then type "save" and you will skip right to it

    }

}
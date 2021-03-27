package com.example.diabeticsinfo;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * @author Jaden Myers
 *
 */
public class AddMedication implements Runnable{

    private WeakReference<Activity> _activityRef;
    private Activity _activity;
    private MedicationPrescriptionGeneralHandler _generalHandler;

    AddMedication(Activity activity, MedicationPrescriptionGeneralHandler generalHandler){
        _activityRef = new WeakReference<Activity>(activity);
        _activity = _activityRef.get();
        _generalHandler = generalHandler;
    }

    public void makeToast(String message){
        if(_activity != null){
            try{
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_activity, message, Toast.LENGTH_SHORT).show();
                    }

                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        SingleMedicationPrescriptionHandler singleMedHandler = new SingleMedicationPrescriptionHandler();

        EditText editMedicationName = _activity.findViewById(R.id.medicationName);
        EditText editDosage = _activity.findViewById(R.id.dosage);
        EditText editNumTablet = _activity.findViewById(R.id.numTablet);
        EditText editTimesTakenADay = _activity.findViewById(R.id.timesTakenADay);
        EditText editRefillsLeft = _activity.findViewById(R.id.refillsLeft);
        EditText editMaxPillCount = _activity.findViewById(R.id.maxPillCount);
        EditText editCurrentPillCount = _activity.findViewById(R.id.currentPillCount);
        EditText editMonth = _activity.findViewById(R.id.month);
        EditText editDay = _activity.findViewById(R.id.day);
        EditText editYear = _activity.findViewById(R.id.year);

        String medicationName = editMedicationName.getText().toString();

        int dosage = -1;
        String dosageText = editDosage.getText().toString();
        if(!dosageText.isEmpty()){
            dosage = Integer.parseInt(dosageText);
        }

        int numTablet = -1;
        String numTabletText = editNumTablet.getText().toString();
        if(!numTabletText.isEmpty()){
            numTablet = Integer.parseInt(numTabletText);
        }

        int timesTakenADay = -1;
        String timesTakenADayText = editTimesTakenADay.getText().toString();
        if(!timesTakenADayText.isEmpty()){
            timesTakenADay = Integer.parseInt(timesTakenADayText);
        }

        int refillsLeft = -1;
        String refillsLeftText = editRefillsLeft.getText().toString();
        if(!refillsLeftText.isEmpty()){
            refillsLeft = Integer.parseInt(refillsLeftText);
        }

        int maxPillCount = -1;
        String maxPillCountText = editMaxPillCount.getText().toString();
        if(!maxPillCountText.isEmpty()){
            maxPillCount = Integer.parseInt(maxPillCountText);
        }

        int currentPillCount = -1;
        String currentPillCountText = editCurrentPillCount.getText().toString();
        if(!currentPillCountText.isEmpty()){
            currentPillCount = Integer.parseInt(currentPillCountText);
        }

        int month = -1;
        String monthText = editMonth.getText().toString();
        if(!monthText.isEmpty()){
            month = Integer.parseInt(monthText);
        }

        int day = -1;
        String dayText = editDay.getText().toString();
        if(!dayText.isEmpty()){
            day = Integer.parseInt(dayText);
        }

        int year = -1;
        String yearText = editYear.getText().toString();
        if(!yearText.isEmpty()){
            year = Integer.parseInt(yearText);
        }

        boolean goodSoFar = true;


        try{
            singleMedHandler.setPrescriptionName(medicationName);
        } catch (Exception e){
            goodSoFar = false;
            makeToast(e.getMessage());
        }


        if(goodSoFar && dosage != -1){
            try{
                singleMedHandler.setMilligramDosageInASingleTablet(dosage);
            } catch (Exception e){
                goodSoFar = false;
                makeToast(e.getMessage());
            }
        }

        if(goodSoFar && numTablet != -1){
            try{
                singleMedHandler.setTakeThisManyTabletsAtaTime(numTablet);
            } catch (Exception e){
                goodSoFar = false;
                makeToast(e.getMessage());
            }
        }

        if(goodSoFar && timesTakenADay != -1){
            try{
                singleMedHandler.setTakeMedicationThisManyTimesADay(timesTakenADay);
            } catch (Exception e){
                goodSoFar = false;
                makeToast(e.getMessage());
            }
        }

        if(goodSoFar && refillsLeft != -1){
            try{
                singleMedHandler.setRefillsRemaining(refillsLeft);
            } catch (Exception e){
                goodSoFar = false;
                makeToast(e.getMessage());
            }
        }

        if(goodSoFar && maxPillCount != -1){
            try{
                singleMedHandler.setMaxPillCountInBottle(maxPillCount);
            } catch (Exception e){
                goodSoFar = false;
                makeToast(e.getMessage());
            }
        }

        if(goodSoFar && currentPillCount != -1){
            try{
                singleMedHandler.setPillsRemainingInBottle(currentPillCount);
            } catch (Exception e){
                goodSoFar = false;
                makeToast(e.getMessage());
            }
        }

        if(goodSoFar && (year != -1) && (month != -1) && (day != -1) ){
            try{
                singleMedHandler.setPrescriptionExpiration(year, month, day);
            } catch (Exception e){
                goodSoFar = false;
                makeToast(e.getMessage());
            }
        }

        if(goodSoFar){
            try{
                _generalHandler.addHandler(singleMedHandler);
                makeToast("Medication added successfully");

                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        editMedicationName.setText("");
                        editDosage.setText("");
                        editNumTablet.setText("");
                        editTimesTakenADay.setText("");
                        editRefillsLeft.setText("");
                        editMaxPillCount.setText("");
                        editCurrentPillCount.setText("");
                        editMonth.setText("");
                        editDay.setText("");
                        editYear.setText("");
                    }

                });

                //Save: This is where you save the general med handler as a json and overwrite the old file

            } catch (Exception e){
                makeToast(e.getMessage());
            }
        }

    }
}

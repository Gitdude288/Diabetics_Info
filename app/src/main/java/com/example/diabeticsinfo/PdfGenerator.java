package com.example.diabeticsinfo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.sql.Date;

/**
 * @author Jaden Myers
 *
 */
public class PdfGenerator {
    Context _context;
    IncludeInPdf _includeInPdf = new IncludeInPdf();
    String report = new String();
    LocalDate _toDate = LocalDate.now();
    LocalDate _fromDate = LocalDate.now();
    String bslFileName = "bslTest.txt";
    public String bpFileName = "bloodpressureTest.txt";
    public String exFileName = "ExerciseFinal.txt";

    public PdfGenerator(Context context){
        _context = context;
    }

    public void setIncludeInPdf(IncludeInPdf includeInPdf){
        _includeInPdf = includeInPdf.clone();
    }

    public void setToDate(LocalDate toDate){
        _toDate = toDate;
    }

    public void setFromDate(LocalDate fromDate) {
        _fromDate = fromDate;
    }

    public void generateBloodSugarReport() throws IOException {
        report += "\nBlood Sugar: \n";

        //bsl object that data gets loaded to
        BSLMeasurement bsl = new BSLMeasurement();
        bsl = getBSLData();
        int days = 1;
        Date start = new Date(_fromDate.getYear() - 1900, _fromDate.getMonthValue() - 1, _fromDate.getDayOfMonth());
        Date end = new Date(_toDate.getYear() - 1900, _toDate.getMonthValue() - 1, _toDate.getDayOfMonth());

        Log.i("start", start.toString());
        Log.i("end", end.toString());

        for (int i = 0; i < bsl.dates.size(); i++) {

            if (bsl.dates.get(i).after(start) && bsl.dates.get(i).before(end)) {
                report += "day " + days + " blood sugar reading: ";
                report += bsl.bsl.get(i) + "\n";
                days += 1;
            }
        }
    }

    public void generateBloodPressureReport() throws IOException {
        report += "\nBlood Pressure: \n";

        BloodPressureMeasurement bp = new BloodPressureMeasurement();
        bp = getBloodPressureData();

        int days = 1;
        Date start = new Date(_fromDate.getYear() - 1900, _fromDate.getMonthValue() - 1, _fromDate.getDayOfMonth());
        Date end = new Date(_toDate.getYear() - 1900, _toDate.getMonthValue() - 1, _toDate.getDayOfMonth());

        Log.i("start", start.toString());
        Log.i("end", end.toString());

        for (int i = 0; i < bp.dates.size(); i++) {

            if (bp.dates.get(i).after(start) && bp.dates.get(i).before(end)) {
                report += "day " + days + " blood pressure reading: ";
                report += bp.bpHigh.get(i) + "/" + bp.bpLow.get(i) + "\n";
                days += 1;
            }
        }
    }

    public void generateExerciseReport() throws IOException {
        report += "\nExercise: \n";

        ExerciseData e = new ExerciseData();
        e = getExerciseData();

        int days = 1;
        Date start = new Date(_fromDate.getYear() - 1900, _fromDate.getMonthValue() - 1, _fromDate.getDayOfMonth());
        Date end = new Date(_toDate.getYear() - 1900, _toDate.getMonthValue() - 1, _toDate.getDayOfMonth());

        Log.i("start", start.toString());
        Log.i("end", end.toString());

        for (int i = 0; i < e.dates.size(); i++) {

            if (e.dates.get(i).after(start) && e.dates.get(i).before(end)) {
                report += "day " + days + " exercised for ";
                report += e.minutes.get(i) + " minutes \n";
                days += 1;
            }
        }
    }

    public boolean generateMedicationReport(){
        // load med handler
        MedicationPrescriptionGeneralHandler medHandler = new MedicationPrescriptionGeneralHandler();
        try{
            SaveLoad saveLoad = new SaveLoad(_context);
            medHandler = saveLoad.loadMedicationList();
        } catch (Exception e){

        }

        boolean areThereAnyMedicationsListed = true;
        List<SingleMedicationPrescriptionHandler> medications = medHandler.cloneAll();

        if(medications.isEmpty()){
            areThereAnyMedicationsListed = false;
        }

        LocalDate iterator = _fromDate;

        report += "Medications Missed:\n";

        while(iterator.isBefore(_toDate)){
            for(SingleMedicationPrescriptionHandler handler: medications){
                List<LocalDateTime> allTheTimesYouTookYourPills = handler.getAllTheTimesYouTookYourPills();
                int numTimesTakePill = 0;

                for(LocalDateTime timeTaken: allTheTimesYouTookYourPills){
                    if(iterator.equals(timeTaken.toLocalDate())){
                        numTimesTakePill += 1;
                    }
                }

                if(handler.getTakeMedicationThisManyTimesADay() > numTimesTakePill && ! iterator.isBefore(handler.getStartDate())){
                    int numTimesForgotToTakePill = handler.getTakeMedicationThisManyTimesADay() - numTimesTakePill;
                    report += iterator.getMonthValue() + "/" + iterator.getDayOfMonth() + "/" + iterator.getYear() + " ";

                    if(numTimesForgotToTakePill == 1){
                        report += numTimesForgotToTakePill + " dose was";
                    } else {
                        report += numTimesForgotToTakePill + " doses were";
                    }

                    report += " missed for " + handler.getPrescriptionName() + "\n";
                }

            }

            iterator = iterator.plusDays(1);
        }

        return areThereAnyMedicationsListed;

    }

    public String generateReport() throws IOException {

        long numDays = _toDate.toEpochDay() - _fromDate.toEpochDay();

        report += numDays + " Day Report\n\n";

        if(generateMedicationReport()){
            if(report.equals(numDays + " Day Report\n\nMedications Missed:\n")){
                report += "No medications were missed\n";
            }
        } else{
            report += "No medications are listed\n";
        }

        if(_includeInPdf.getIncludeBloodSugar()){
            generateBloodSugarReport();
        }

        if(_includeInPdf.getIncludeBloodPressure()){
            generateBloodPressureReport();
        }

        if(_includeInPdf.getIncludeExercise()){
            generateExerciseReport();
        }

        return report;
    }

    public BSLMeasurement getBSLData() throws IOException {
        File logFile = new File(bslFileName);
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
        file = _context.openFileInput(bslFileName);

        InputStreamReader isr = new InputStreamReader(file);
        BufferedReader bfr = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();

        String reader;

        while ((reader = bfr.readLine()) != null) {
            sb.append(reader).append("");
        }

        reader = sb.toString();

        BSLMeasurement bl = new BSLMeasurement();

        Gson gson = new Gson();
        bl = gson.fromJson(reader, BSLMeasurement.class);

        Log.i("addMedication", bl.getData());
        file.close();

        return bl;
    }

    public BloodPressureMeasurement getBloodPressureData() throws IOException {
        File logFile = new File(bpFileName);
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
        file = _context.openFileInput(bpFileName);

        InputStreamReader isr = new InputStreamReader(file);
        BufferedReader bfr = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();

        String reader;

        while ((reader = bfr.readLine()) != null) {
            sb.append(reader).append("");
        }

        reader = sb.toString();

        Gson gson = new Gson();

        BloodPressureMeasurement b = new BloodPressureMeasurement();
        b = gson.fromJson(reader, BloodPressureMeasurement.class);

        Log.i("addBloodPressure", b.getData());
        file.close();

        return b;
    }

    public ExerciseData getExerciseData() throws IOException {
        File logFile = new File(exFileName);
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
        file = _context.openFileInput(exFileName);

        InputStreamReader isr = new InputStreamReader(file);
        BufferedReader bfr = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();

        String reader;

        while ((reader = bfr.readLine()) != null) {
            sb.append(reader).append("");
        }

        reader = sb.toString();

        Gson gson = new Gson();
        ExerciseData ed = new ExerciseData();
        ed = gson.fromJson(reader, ExerciseData.class);

        Log.i("addMedication", ed.getData());
        file.close();

        return ed;
    }
}

package com.example.diabeticsinfo;

import android.content.Context;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

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

    public void generateBloodSugarReport(){
        report += "\nBlood Sugar: \n";
        report += "day 1 blood sugar reading: ?\n";
        report += "day 2 blood sugar reading: ?\n";
        report += "day 3 blood sugar reading: ?\n";
        report += "day 4 blood sugar reading: ?\n";
        report += "day 5 blood sugar reading: ?\n";
        report += "day 6 blood sugar reading: ?\n";
    }

    public void generateBloodPressureReport(){
        report += "\nBlood Pressure: \n";
        report += "day 1 blood pressure reading: ?\n";
        report += "day 2 blood pressure reading: ?\n";
        report += "day 3 blood pressure reading: ?\n";
        report += "day 4 blood pressure reading: ?\n";
        report += "day 5 blood pressure reading: ?\n";
        report += "day 6 blood pressure reading: ?\n";
    }

    public void generateExerciseReport(){
        report += "\nExercise: \n";
        report += "day 1 exercise amount: ?\n";
        report += "day 2 exercise amount: ?\n";
        report += "day 3 exercise amount: ?\n";
        report += "day 4 exercise amount: ?\n";
        report += "day 5 exercise amount: ?\n";
        report += "day 6 exercise amount: ?\n";
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
                    report += handler.getPrescriptionName() + ": ";

                    if(numTimesForgotToTakePill == 1){
                        report += numTimesForgotToTakePill + " dose was";
                    } else {
                        report += numTimesForgotToTakePill + " doses were";
                    }

                    report += " missed on " + iterator.getMonthValue() + "/" + iterator.getDayOfMonth() + "/" + iterator.getYear() + "\n";
                }

            }

            iterator = iterator.plusDays(1);
        }

        return areThereAnyMedicationsListed;

    }

    public String generateReport(){

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

}

package com.example.diabeticsinfo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

/**
 * @author Jaden Myers
 *
 */
public class PdfGenerator {
    IncludeInPdf _includeInPdf = new IncludeInPdf();
    String report = new String();
    LocalDate _toDate = LocalDate.now();
    LocalDate _fromDate = LocalDate.now();

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

    public void generateMedicationReport(){
        report += "Medications taken: \n";
        report += "day 1 list of meds taken: ?\n";
        report += "day 2 list of meds taken: ?\n";
        report += "day 3 list of meds taken: ?\n";
        report += "day 4 list of meds taken: ?\n";
        report += "day 5 list of meds taken: ?\n";
        report += "day 6 list of meds taken: ?\n";
    }

    public String generateReport(){

        long numDays = _toDate.toEpochDay() - _fromDate.toEpochDay();

        report += numDays + " Day Report\n\n";

        generateMedicationReport();

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

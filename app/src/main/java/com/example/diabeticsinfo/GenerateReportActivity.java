package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.time.LocalDate;

/**
 * @author Jaden Myers
 *
 */
public class GenerateReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);

        LocalDate today = LocalDate.now();
        LocalDate fromDate = today.minusDays(90);

        String fromMM = String.valueOf(fromDate.getMonthValue());
        String fromDD = String.valueOf(fromDate.getDayOfMonth());
        String fromYY = String.valueOf(fromDate.getYear());
        String toMM = String.valueOf(today.getMonthValue());
        String toDD = String.valueOf(today.getDayOfMonth());
        String toYY = String.valueOf(today.getYear());

        EditText fromMonth = findViewById(R.id.fromMonth);
        EditText fromDay = findViewById(R.id.fromDay);
        EditText fromYear = findViewById(R.id.fromYear);
        EditText toMonth = findViewById(R.id.toMonth);
        EditText toDay = findViewById(R.id.toDay);
        EditText toYear = findViewById(R.id.toYear);

        fromMonth.setText(fromMM);
        fromDay.setText(fromDD);
        fromYear.setText(fromYY);
        toMonth.setText(toMM);
        toDay.setText(toDD);
        toYear.setText(toYY);
    }

    public void goToReportPreview(View view) {

        EditText fromMonth = findViewById(R.id.fromMonth);
        EditText fromDay = findViewById(R.id.fromDay);
        EditText fromYear = findViewById(R.id.fromYear);
        EditText toMonth = findViewById(R.id.toMonth);
        EditText toDay = findViewById(R.id.toDay);
        EditText toYear = findViewById(R.id.toYear);

        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        boolean datesAreGood = true;

        try{
            int fromMM = Integer.parseInt(fromMonth.getText().toString());
            int fromDD = Integer.parseInt(fromDay.getText().toString());
            int fromYYYY = Integer.parseInt(fromYear.getText().toString());
            int toMM = Integer.parseInt(toMonth.getText().toString());
            int toDD = Integer.parseInt(toDay.getText().toString());
            int toYYYY = Integer.parseInt(toYear.getText().toString());

            fromDate = LocalDate.of(fromYYYY, fromMM, fromDD);
            toDate = LocalDate.of(toYYYY, toMM, toDD);
        } catch (Exception e){
            datesAreGood = false;
            Toast.makeText(this, "To and from dates must be filled out correctly", Toast.LENGTH_SHORT).show();
        }

        if(datesAreGood){
            Switch bloodSugar = findViewById(R.id.switchBloodSugar);
            Switch bloodPressure = findViewById(R.id.switchBloodPressure);
            Switch exercise = findViewById(R.id.switchExercise);

            boolean includeBloodSugar = bloodSugar.isChecked();
            boolean includeBloodPressure = bloodPressure.isChecked();
            boolean includeExercise = exercise.isChecked();

            IncludeInPdf includeInPdf = new IncludeInPdf();
            includeInPdf.setIncludeBloodSugar(includeBloodSugar);
            includeInPdf.setIncludeBloodPressure(includeBloodPressure);
            includeInPdf.setIncludeExercise(includeExercise);

            PdfGenerator pdfGenerator = new PdfGenerator();
            pdfGenerator.setFromDate(fromDate);
            pdfGenerator.setToDate(toDate);
            pdfGenerator.setIncludeInPdf(includeInPdf);
            String report = pdfGenerator.generateReport();

            Intent intent = new Intent(this, ReportPreviewActivity.class);
            intent.putExtra("report", report);
            startActivity(intent);
        }

    }

}
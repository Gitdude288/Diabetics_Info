package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class GenerateReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);

        Calendar today = Calendar.getInstance();
        Calendar fromDate = Calendar.getInstance();
        fromDate.add(Calendar.DAY_OF_MONTH, -90);

        String fromMM = String.valueOf(fromDate.get(Calendar.MONTH) + 1);
        String fromDD = String.valueOf(fromDate.get(Calendar.DAY_OF_MONTH));
        String fromYY = String.valueOf(fromDate.get(Calendar.YEAR));
        String toMM = String.valueOf(today.get(Calendar.MONTH) + 1);
        String toDD = String.valueOf(today.get(Calendar.DAY_OF_MONTH));
        String toYY = String.valueOf(today.get(Calendar.YEAR));

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

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goTo90DayReport(View view) {
        Intent intent = new Intent(this, ReportPreviewActivity.class);
        startActivity(intent);
    }

    public void goToCustomDuratioinReport(View view) {
        Intent intent = new Intent(this, ReportPreviewActivity.class);
        startActivity(intent);
    }

}
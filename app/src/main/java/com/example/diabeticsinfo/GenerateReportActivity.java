package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GenerateReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
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
package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class ReportPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_preview);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.activity_report_preview, menu);
        return true;
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, GenerateReportActivity.class);
        startActivity(intent);
    }
}
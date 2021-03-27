package com.example.diabeticsinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Jaden Myers
 *
 */
public class ReportPreviewActivity extends AppCompatActivity {

    private String _report;
    TextView reportPreview;
    TextView filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_preview);

        _report = getIntent().getStringExtra("report");
        reportPreview = findViewById(R.id.reportPreview);
        reportPreview.setText(_report);

        filePath = findViewById(R.id.filePath);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.activity_report_preview, menu);
        return true;
    }

    public void savePDF(View view){
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300,600,1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Paint paint = new Paint();
        int x = 10;
        int y = 25;

        for(String line: _report.split("\n")){
            page.getCanvas().drawText(line, x, y, paint);
            y += paint.descent() - paint.ascent();
        }

        pdfDocument.finishPage(page);

        String allFiles = "";
        String filename = "/storage/emulated/0/Download/Test.pdf";
        //String filename1 = "/storage/emulated/0/Android/data/Test.pdf";
        String filename1 = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/Test.pdf";
        String filename2 = Environment.getExternalStorageDirectory().getPath() + "/Test.pdf";
        String filename3 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Test.pdf";
        allFiles = filename + "\n" + filename1 + "\n" + filename2 + "\n" + filename3;
        //reportPreview.setText(allFiles);
        File saveFile = new File(filename);
        File saveFile1 = new File(filename1);
        File saveFile2 = new File(filename2);
        File saveFile3 = new File(filename3);

        try{
            //pdfDocument.writeTo(new FileOutputStream(saveFile));
            //pdfDocument.writeTo(new FileOutputStream(saveFile1));
            //pdfDocument.writeTo(new FileOutputStream(saveFile2));
            pdfDocument.writeTo(new FileOutputStream(saveFile3));
            Toast.makeText(this, "Saved to Downloads", Toast.LENGTH_SHORT).show();
            filePath.setText("PDF saved to: Downloads");

        } catch (Exception e){
            reportPreview.setText(e.getMessage());
            try {
                pdfDocument.writeTo(new FileOutputStream(saveFile1));
            }catch (Exception e1){
                Toast.makeText(this, "Error in saving PDF", Toast.LENGTH_SHORT).show();
                reportPreview.setText("Error");
            }
        }

        pdfDocument.close();
    }

}
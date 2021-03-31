package com.example.diabeticsinfo;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


import static android.content.Context.MODE_PRIVATE;

public class SaveLoad {
    private Context _context;
    private String medFileName = "medHandler.txt";

    public SaveLoad(Context context){
        _context = context;
    }

    public void saveMedicationList (MedicationPrescriptionGeneralHandler medHandler) throws Exception {
        Gson g = new Gson();
        String json = g.toJson(medHandler);

        FileOutputStream file = null;
        file = _context.openFileOutput(medFileName, MODE_PRIVATE);
        file.write(json.getBytes());
        file.close();

    }

    public MedicationPrescriptionGeneralHandler loadMedicationList() throws IOException {
        MedicationPrescriptionGeneralHandler medHandler = new MedicationPrescriptionGeneralHandler();
        File logFile = new File(medFileName);
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
        file = _context.openFileInput (medFileName);

        InputStreamReader isr = new InputStreamReader(file);
        BufferedReader bfr = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();

        String reader;

        while ((reader = bfr.readLine()) != null) {
            sb.append(reader).append("");
        }

        reader = sb.toString();

        Gson gson = new Gson();
        medHandler = gson.fromJson(reader, MedicationPrescriptionGeneralHandler.class);

        //Log.i("addMedication", m.getMeds());
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return medHandler;
    }
}


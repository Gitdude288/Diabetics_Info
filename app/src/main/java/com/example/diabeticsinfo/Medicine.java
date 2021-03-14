package com.example.diabeticsinfo;

import java.util.ArrayList;
import java.util.List;

public class Medicine {

    public List<String> medicine = new ArrayList<String>();
    public List<Integer> doses = new ArrayList<Integer>();

    public void addMedication(String med) {
        medicine.add(med);
    }

    public void addDose(Integer dose) {
        doses.add(dose);
    }

    public String getMeds(){
        String raw = "";
        for (int i = 0; i < medicine.size(); i++) {
            raw += medicine.get(i);
            raw += " ";
            raw += doses.get(i);
            raw += " ";
        }
        return raw;
    }
}

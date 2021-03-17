package com.example.diabeticsinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of the user's medication. It has a list of strings
 * called medicine and a list of ints called dosages.
 * @version 1.0
 * @author Matt
 * */
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

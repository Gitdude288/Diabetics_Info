package com.example.diabeticsinfo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BloodPressureMeasurement {
    public List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
    public List<Integer> bpHigh = new ArrayList<Integer>();
    public List<Integer> bpLow = new ArrayList<Integer>();

    public void addDate(Date date) {
        dates.add(date);
    }

    public void addBP(Integer h, Integer l) {
        bpHigh.add(h);
        bpLow.add(l);
    }

    public String getData(){
        String raw = "";
        for (int i = 0; i < dates.size(); i++) {
            raw += dates.get(i).toString();
            raw += " ";
            raw += bpHigh.get(i);
            raw += " ";
            raw += bpLow.get(i);
            raw += " ";
        }
        return raw;
    }
}

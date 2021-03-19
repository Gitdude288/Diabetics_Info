package com.example.diabeticsinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BloodPressureMeasurement {
    public List<Date> dates = new ArrayList<Date>();
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
            raw += dates.get(i);
            raw += " ";
            raw += bpHigh.get(i);
            raw += " ";
            raw += bpLow.get(i);
            raw += " ";
        }
        return raw;
    }
}

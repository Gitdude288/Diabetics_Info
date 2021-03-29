package com.example.diabeticsinfo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BSLMeasurement {
    public List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
    public List<Integer> bsl = new ArrayList<Integer>();

    public void addDate(Date date) {
        dates.add(date);
    }

    public void addBSL(Integer b) {
        bsl.add(b);
    }

    public String getData(){
        String raw = "";
        for (int i = 0; i < dates.size(); i++) {
            raw += dates.get(i).toString();
            raw += " ";
            raw += bsl.get(i);
            raw += " ";
        }
        return raw;
    }
}

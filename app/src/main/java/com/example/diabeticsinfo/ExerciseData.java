package com.example.diabeticsinfo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExerciseData {
    public List<Date> dates = new ArrayList<Date>();
    public List<Integer> minutes = new ArrayList<Integer>();

    public void addDate(Date d) {
        dates.add(d);
    }

    public void addMinutes(Integer dose) {
        minutes.add(dose);
    }

    public String getData(){
        String raw = "";
        for (int i = 0; i < dates.size(); i++) {
            raw += dates.get(i).toString();
            raw += " ";
            raw += minutes.get(i);
            raw += " ";
        }
        return raw;
    }
}

package com.example.diabeticsinfo;

import java.util.ArrayList;
import java.util.List;

public class ExerciseData {
    public List<String> dates = new ArrayList<String>();
    public List<Integer> minutes = new ArrayList<Integer>();

    public void addDate(String med) {
        dates.add(med);
    }

    public void addMinutes(Integer dose) {
        minutes.add(dose);
    }

    public String getData(){
        String raw = "";
        for (int i = 0; i < dates.size(); i++) {
            raw += dates.get(i);
            raw += " ";
            raw += minutes.get(i);
            raw += " ";
        }
        return raw;
    }
}

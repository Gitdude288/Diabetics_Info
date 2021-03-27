package com.example.diabeticsinfo;

/**
 * @author Jaden Myers
 *
 */
public class IncludeInPdf {
    private boolean _includeBloodSugar;
    private boolean _includeBloodPressure;
    private boolean _includeExercise;

    public boolean getIncludeBloodSugar(){
        return _includeBloodSugar;
    }

    public boolean getIncludeBloodPressure(){
        return _includeBloodPressure;
    }

    public boolean getIncludeExercise() {
        return _includeExercise;
    }

    public void setIncludeBloodPressure(boolean includeBloodPressure) {
        _includeBloodPressure = includeBloodPressure;
    }

    public void setIncludeBloodSugar(boolean includeBloodSugar) {
        _includeBloodSugar = includeBloodSugar;
    }

    public void setIncludeExercise(boolean includeExercise) {
        _includeExercise = includeExercise;
    }

    public IncludeInPdf clone(){
        IncludeInPdf clone = new IncludeInPdf();
        clone.setIncludeBloodPressure(this.getIncludeBloodPressure());
        clone.setIncludeBloodSugar(this.getIncludeBloodSugar());
        clone.setIncludeExercise(this.getIncludeExercise());
        return clone;
    }
}

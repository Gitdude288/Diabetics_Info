package medication_prescription_handler;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleMedicationPrescriptionHandler {

    private LocalDate _prescriptionExpiration;
    private String _prescriptionName;
    private int _takeMedicationThisManyTimesADay;
    private int _milligramDosageInASinglePill;
    private int _milligramDosagePrescribed;
    private int _pillsRemainingInBottle;

    public String getPrescriptionName(){
        return _prescriptionName;
    }
    public LocalDate getPrescriptionExpiration() {
        return _prescriptionExpiration;
    }
    public int getTakeMedicationThisManyTimesADay(){return _takeMedicationThisManyTimesADay;}
    public int getMilligramDosageInASinglePill(){return _milligramDosageInASinglePill;}
    public int getMilligramDosagePrescribed(){return _milligramDosagePrescribed;}
    public int getPillsRemainingInBottle(){return _pillsRemainingInBottle;}

    public void setTakeMedicationThisManyTimesADay(int takeMedicationThisManyTimesADay){}
    public void setMilligramDosageInASinglePill(int milligramDosageInaSinglePill){}
    public void setMilligramDosagePrescribed(int milligramDosagePrescribed){}
    public void setPillsRemainingInBottle(int pillsRemainingInBottle){}

    public void set_prescriptionName(String prescriptionName) throws Exception{

        Pattern pattern = Pattern.compile("[^A-Za-z]");
        Matcher matcher = pattern.matcher(prescriptionName);
        boolean containsBadCharacters = matcher.find();

       if(prescriptionName.isEmpty() || prescriptionName.isBlank()){
           MedHandlerException nullPrescriptionName = new MedHandlerException("Prescription Name must be filled out");
           System.out.println(nullPrescriptionName.getMessage());
           System.out.println();
           throw nullPrescriptionName;
        }else if(prescriptionName.length() < 4) {
           MedHandlerException tooShortPrescriptionName = new MedHandlerException("Prescription Name must be at least four letters long");
           System.out.println(tooShortPrescriptionName.getMessage());
           System.out.println();
           throw tooShortPrescriptionName;
        }else if(containsBadCharacters){
           MedHandlerException nonLetterPrescriptionName = new MedHandlerException("Prescription Name must only contain letters");
           System.out.println(nonLetterPrescriptionName.getMessage());
           System.out.println();
           throw nonLetterPrescriptionName;
       }else{
           _prescriptionName = prescriptionName;
       }
    }

    public void setPrescriptionExpiration(int year, int month, int day) throws Exception{
        try {
            LocalDate prescriptionExpiration = LocalDate.of(year, month, day);
            _prescriptionExpiration = prescriptionExpiration;
        } catch (Exception e){
            MedHandlerException invalidDate = new MedHandlerException("Invalid date");
            System.out.println(invalidDate.getMessage());
            System.out.println();
            throw invalidDate;
        }
    }

    public boolean isCloseToPrescriptionExpiration(){
        boolean isCloseToPrescriptionExpiration = false;

        LocalDate today = LocalDate.now();

        Period timeLeft = Period.between(today, _prescriptionExpiration);
        int years = timeLeft.getYears();
        int months = timeLeft.getMonths();
        int days = timeLeft.getDays();

        if((years < 1) && (months < 1) && (days < 6)){
            isCloseToPrescriptionExpiration = true;
        }

        return isCloseToPrescriptionExpiration;
    }

    public void sendPrescriptionExpirationAlert() {
        LocalDate today = LocalDate.now();
        Period timeLeft = Period.between(today, _prescriptionExpiration);
        PrescriptionExpirationAlert alert = new PrescriptionExpirationAlert(timeLeft, _prescriptionName);
        alert.sendAlert();
    }

    public void sendTakePillAlert(){
        System.out.println("Remember to take your pill for " + _prescriptionName);
        System.out.println();
    }

    public void sendLowPillCountAlert(){

    }

}

package medication_prescription_handler;

import java.time.LocalDate;
import java.time.Period;

public class SingleMedicationPrescriptionHandler {

    private LocalDate prescriptionExpiration;
    private String prescriptionName;
    private int takeMedicationThisManyTimesADay;
    private int milligramDosage;

    public boolean isCloseToPrescriptionExpiration(){
        boolean isCloseToPrescriptionExpiration = false;

        LocalDate today = LocalDate.now();

        Period timeLeft = Period.between(today, prescriptionExpiration);
        int years = timeLeft.getYears();
        int months = timeLeft.getMonths();
        int days = timeLeft.getDays();

        if((years < 1) && (months < 1) && (days < 6)){
            isCloseToPrescriptionExpiration = true;
        }

        return isCloseToPrescriptionExpiration;
    }

    public void setPrescriptionName(String name){
        prescriptionName = name;
    }

    public void setPrescriptionExpiration(int year, int month, int day) throws Exception{
        try {
            LocalDate temp = LocalDate.of(year, month, day);
            prescriptionExpiration = temp;
        } catch (Exception e){
            MedHandlerException invalidDate = new MedHandlerException("Invalid date");
            System.out.println(invalidDate.getMessage());
            System.out.println();
            throw invalidDate;
        }


    }

    public String getPrescriptionName(){
        return prescriptionName;
    }

    public void sendPrescriptionExpirationAlert() {
        LocalDate today = LocalDate.now();
        Period timeLeft = Period.between(today, prescriptionExpiration);
        PrescriptionExpirationAlert alert = new PrescriptionExpirationAlert(timeLeft, prescriptionName);
        alert.sendAlert();
    }

    public void sendTakePillAlert(){
        System.out.println("Remember to take your pill for " + prescriptionName);
        System.out.println();
    }

    public void sendLowPillCountAlert(){

    }

    public LocalDate getPrescriptionExpiration() {
        return prescriptionExpiration;
    }
}

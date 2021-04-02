package com.example.diabeticsinfo;

import android.view.View;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import com.example.diabeticsinfo.Notifier;

/**
 * @author Jaden Myers
 *
 */
public class MedicationPrescriptionGeneralHandler {
    private List<SingleMedicationPrescriptionHandler> prescriptionList;

    private LocalDateTime lastDateAndTimeTakePillsAlertWasSent;
    private int pillAlertYear;
    private int pillAlertMonth;
    private int pillAlertDay;
    private int pillAlertHour;
    private int pillAlertMinute;

    public MedicationPrescriptionGeneralHandler(){
        prescriptionList = new ArrayList<>();
    }

    public void recoverAllDates(){
        try{
            lastDateAndTimeTakePillsAlertWasSent = LocalDateTime.of(pillAlertYear, pillAlertMonth, pillAlertDay, pillAlertHour, pillAlertMinute);
        } catch(Exception e){

        }


        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            handler.recoverAllDates();
        }
    }

    private void backUpTakePillAlertDate(){
        pillAlertYear = lastDateAndTimeTakePillsAlertWasSent.getYear();
        pillAlertMonth = lastDateAndTimeTakePillsAlertWasSent.getMonthValue();
        pillAlertDay = lastDateAndTimeTakePillsAlertWasSent.getDayOfMonth();
        pillAlertHour = lastDateAndTimeTakePillsAlertWasSent.getHour();
        pillAlertMinute = lastDateAndTimeTakePillsAlertWasSent.getMinute();
    }

    private int getMaxTimesADay(){
        int maxTimesADay = 0;
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(handler.getTakeMedicationThisManyTimesADay() > maxTimesADay){
                maxTimesADay = handler.getTakeMedicationThisManyTimesADay();
            }
        }
        return maxTimesADay;
    }

    private String sendTakePillAlert(){
        String takePillsMessage = new String();
        LocalDate today = LocalDate.now();

        if( ! prescriptionList.isEmpty()){
            if(lastDateAndTimeTakePillsAlertWasSent == null){
                lastDateAndTimeTakePillsAlertWasSent = LocalDateTime.now();
                backUpTakePillAlertDate();
                takePillsMessage = "Remember to take your pills";
            } else if (lastDateAndTimeTakePillsAlertWasSent.toLocalDate().equals(today)){
                int numberOfHoursBetweenAlerts = 24 / getMaxTimesADay();
                LocalTime rightNow = LocalTime.now();
                LocalTime timeOfLastAlert = lastDateAndTimeTakePillsAlertWasSent.toLocalTime();
                int currentHour = rightNow.getHour();
                int hourOfLastAlert = timeOfLastAlert.getHour();

                if((currentHour - hourOfLastAlert) >= numberOfHoursBetweenAlerts){
                    lastDateAndTimeTakePillsAlertWasSent = LocalDateTime.now();
                    backUpTakePillAlertDate();
                    takePillsMessage = "Remember to take your pills";
                }

            } else {
                lastDateAndTimeTakePillsAlertWasSent = LocalDateTime.now();
                backUpTakePillAlertDate();
                takePillsMessage = "Remember to take your pills";
            }
        }

        return takePillsMessage;
    }

    private List<String> sendPrescriptionExpirationAlerts(){
        List<String> prescriptionExpirationMessages = new ArrayList<>();
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(handler.isCloseToPrescriptionExpiration() && handler.haveNotAlreadySentExpirationAlertToday()){
                handler.setDateExpirationAlertWasLastSent(LocalDate.now());
                prescriptionExpirationMessages.add(handler.sendPrescriptionExpirationAlert());
            }
        }

        return prescriptionExpirationMessages;
    }

    private List<String> sendLowPillCountAlerts(){
        List<String> lowPillCountMessages = new ArrayList<>();
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(handler.isCloseToRunningOut() && handler.haveNotAlreadySentPillCountAlertToday()){
                handler.setDatePillCountAlertWasLastSent(LocalDate.now());
                lowPillCountMessages.add(handler.sendLowPillCountAlert());
            }
        }

        return lowPillCountMessages;
    }

    public SingleMedicationPrescriptionHandler cloneFromList(String prescriptionName, int dosage) {
        SingleMedicationPrescriptionHandler clone = new SingleMedicationPrescriptionHandler();

        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(     handler.getPrescriptionName().equals(prescriptionName) &&
                    handler.getMilligramDosageInASingleTablet() == dosage){
                clone = handler.clone();
                return clone;
            }
        }

        return null;
    }

    public List<SingleMedicationPrescriptionHandler> cloneAll(){
        List<SingleMedicationPrescriptionHandler> cloneList = new ArrayList<>();

        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            cloneList.add(handler.clone());
        }

        return cloneList;
    }

    public void addHandler(SingleMedicationPrescriptionHandler newHandler) throws Exception {
        if(newHandler.isValid()){
            if(newHandler.getAllTheTimesYouTookYourPills().isEmpty()){
                LocalDateTime now = LocalDateTime.now();
                newHandler.setStartDate(now);
            }

            boolean newHandlerIsUnique = true;
            for(SingleMedicationPrescriptionHandler existingHandler: prescriptionList){

                if(     newHandler.getPrescriptionName().equals(existingHandler.getPrescriptionName()) &&
                        newHandler.getMilligramDosageInASingleTablet() == existingHandler.getMilligramDosageInASingleTablet()
                ){
                    newHandlerIsUnique = false;
                }
            }

            if(newHandlerIsUnique){
                prescriptionList.add(newHandler.clone());
            } else{
                Exception duplicate = new Exception("Didn't save! You already have saved a prescription of the same name and dosage");
                throw duplicate;
            }

        }

    }

    public void takePill(String prescriptionName, int dosage, LocalDateTime timeTaken){
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(     handler.getPrescriptionName().equals(prescriptionName) &&
                    handler.getMilligramDosageInASingleTablet() == dosage)
            {
                handler.takePill(timeTaken);
            }
        }
    }

    public void delete(String prescriptionName, int dosage){

        List <SingleMedicationPrescriptionHandler> tempList = new ArrayList<>();
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            tempList.add(handler.clone());
        }

        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(     handler.getPrescriptionName().equals(prescriptionName) &&
                    handler.getMilligramDosageInASingleTablet() == dosage)
            {
                tempList.remove(prescriptionList.indexOf(handler));
            }
        }

        prescriptionList = tempList;
    }

    public void replace(String prescriptionNameOfObsolete, int dosageOfObsolete, SingleMedicationPrescriptionHandler replacement) throws Exception {
        if(replacement.isValid()){
            delete(prescriptionNameOfObsolete,dosageOfObsolete);
            addHandler(replacement);
        }
    }

    public void refillBottle(String prescriptionName, int dosage){

        for(SingleMedicationPrescriptionHandler handler: prescriptionList) {
            if (handler.getPrescriptionName().equals(prescriptionName) &&
                    handler.getMilligramDosageInASingleTablet() == dosage)
            {
                try {
                    handler.setPillsRemainingInBottle(handler.getMaxPillCountInBottle());
                    if(handler.getRefillsRemaining() > 0){
                        handler.setRefillsRemaining(handler.getRefillsRemaining() - 1);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    public List<String> getMessages(){
        List<String> masterListOfMessages = new ArrayList<>();
        masterListOfMessages.add(sendTakePillAlert());
        masterListOfMessages.addAll(sendPrescriptionExpirationAlerts());
        masterListOfMessages.addAll(sendLowPillCountAlerts());

        List<String> tempList = new ArrayList<>();
        for(String message: masterListOfMessages){
            if(! message.isEmpty()){
                tempList.add(message);
            }
        }

        masterListOfMessages = tempList;

        return masterListOfMessages;

    }

}

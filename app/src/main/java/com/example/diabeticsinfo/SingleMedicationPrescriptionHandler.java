package com.example.diabeticsinfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jaden Myers
 *
 */
public class SingleMedicationPrescriptionHandler {
    private LocalDate _prescriptionExpiration;
    private int _expirationYear;
    private int _expirationMonth;
    private int _expirationDay;

    private String _prescriptionName;
    private int _takeMedicationThisManyTimesADay;
    private int _milligramDosageInASingleTablet;
    private int _takeThisManyTabletsAtaTime;
    private int _maxPillCountInBottle;
    private int _pillsRemainingInBottle;
    private int _refillsRemaining;

    private LocalDate _datePillCountAlertWasLastSent;
    private int _pillCountAlertYear;
    private int _pillCountAlertMonth;
    private int _pillCountAlertDay;

    private LocalDate _dateExpirationAlertWasLastSent;
    private int _expirationAlertYear;
    private int _expirationAlertMonth;
    private int _expirationAlertDay;

    private LocalDateTime _startDate;
    int _startDateYear;
    int _startDateMonth;
    int _startDateDay;
    private int _startDateHour;
    private int _startDateMinute;

    private List<LocalDateTime> allTheTimesYouTookYourPills;
    private List<String> stringsAllTheTimesYouTookYourPills;



    public SingleMedicationPrescriptionHandler(){
        _maxPillCountInBottle = -1;
        _pillsRemainingInBottle = -1;
        _refillsRemaining = -1;
        allTheTimesYouTookYourPills = new ArrayList<>();
        stringsAllTheTimesYouTookYourPills = new ArrayList<>();
    }

    private void backUpAllTheTimesYouTookYourPills(){

        stringsAllTheTimesYouTookYourPills = new ArrayList<>();

        for(LocalDateTime timeYouTookPill: allTheTimesYouTookYourPills){
            String timeYouTookPillString = timeYouTookPill.getYear() + "\n" + timeYouTookPill.getMonthValue() + "\n" + timeYouTookPill.getDayOfMonth() + "\n" + timeYouTookPill.getHour() + "\n" + timeYouTookPill.getMinute();
            stringsAllTheTimesYouTookYourPills.add(timeYouTookPillString);
        }
    }

    public void putAllTheTimesYouTookYourPillsInChronologicalOrder(){
        List<LocalDateTime> clone = new ArrayList<>();
        for(LocalDateTime timeTaken: allTheTimesYouTookYourPills){
            clone.add(timeTaken);
        }
        List<LocalDateTime> orderedList = new ArrayList<>();


        while(!clone.isEmpty()){
            List<LocalDateTime> temp = new ArrayList<>();
            LocalDateTime earliestDate = LocalDateTime.now();
            earliestDate = earliestDate.plusYears(100);

            for(LocalDateTime timeTaken: clone){
                if(timeTaken.isBefore(earliestDate)){
                    earliestDate = timeTaken;
                }
            }

            for(LocalDateTime timeTaken: clone){
                if(timeTaken.equals(earliestDate)){
                    orderedList.add(timeTaken);
                } else{
                    temp.add(timeTaken);
                }

                clone = temp;
            }
        }

        allTheTimesYouTookYourPills = orderedList;
        backUpAllTheTimesYouTookYourPills();
    }

    public void recoverAllDates(){
        try{
            _prescriptionExpiration = LocalDate.of(_expirationYear, _expirationMonth, _expirationDay);
        }catch(Exception e){

        }

        try{
            _datePillCountAlertWasLastSent = LocalDate.of(_pillCountAlertYear, _pillCountAlertMonth, _pillCountAlertDay);
        }catch(Exception e){

        }

        try{
            _dateExpirationAlertWasLastSent = LocalDate.of(_expirationAlertYear, _expirationAlertMonth, _expirationAlertDay);
        }catch(Exception e){

        }

        try{
            _startDate = LocalDateTime.of(_startDateYear, _startDateMonth, _startDateDay, _startDateHour, _startDateMinute);
        }catch(Exception e){

        }

        allTheTimesYouTookYourPills = new ArrayList<>();

        for(String tookPillString: stringsAllTheTimesYouTookYourPills){
            int data[] = new int[5];
            int i = 0;
            for(String dataString: tookPillString.split("\n")){
                data[i] = Integer.parseInt(dataString);
                i += 1;
            }

            LocalDateTime timePillWasTaken = LocalDateTime.of(data[0], data[1], data[2], data[3], data[4]);
            allTheTimesYouTookYourPills.add(timePillWasTaken);

            try{

            } catch(Exception e){

            }

        }
    }

    public LocalDate getStartDate(){
        return _startDate.toLocalDate();
    }

    public String getPrescriptionName(){
        return _prescriptionName;
    }

    public LocalDate getPrescriptionExpiration() {
        if(_prescriptionExpiration == null){
            try{
                setPrescriptionExpiration(_expirationYear, _expirationMonth, _expirationDay);
            } catch(Exception e){

            }
        }
        return _prescriptionExpiration;
    }

    public int getTakeMedicationThisManyTimesADay(){return _takeMedicationThisManyTimesADay;}
    public int getMilligramDosageInASingleTablet(){return _milligramDosageInASingleTablet;}
    public int getTakeThisManyTabletsAtaTime(){return _takeThisManyTabletsAtaTime;}
    public int getMaxPillCountInBottle(){return _maxPillCountInBottle;}
    public int getPillsRemainingInBottle(){return _pillsRemainingInBottle;}
    public int getRefillsRemaining(){return _refillsRemaining;}

    public List<LocalDateTime> getAllTheTimesYouTookYourPills(){
        putAllTheTimesYouTookYourPillsInChronologicalOrder();
        List<LocalDateTime> clone = new ArrayList<>();
        for(LocalDateTime timeTaken: allTheTimesYouTookYourPills){
            clone.add(timeTaken);
        }

        return clone;
    }

    public List<String> getStringsAllTheTimesYouTookYourPills(){
        List<String> clone = new ArrayList<>();
        for(String timeTaken: stringsAllTheTimesYouTookYourPills){
            clone.add(timeTaken);
        }

        return clone;
    }

    public void setTakeMedicationThisManyTimesADay(int takeMedicationThisManyTimesADay) throws Exception{
        if(takeMedicationThisManyTimesADay < 1){
            Exception takeMedLessThanOnceADay = new Exception("Number of times a day to take medication must be greater than zero");
            throw takeMedLessThanOnceADay;
        } else {
            _takeMedicationThisManyTimesADay = takeMedicationThisManyTimesADay;
        }
    }

    public void setMilligramDosageInASingleTablet(int milligramDosageInASinglePill) throws Exception{
        if(milligramDosageInASinglePill < 1){
            Exception singlePillDosageIsLessThanOneMg = new Exception("Dosage of a single pill must be greater than zero");
            throw singlePillDosageIsLessThanOneMg;
        } else {
            _milligramDosageInASingleTablet = milligramDosageInASinglePill;
        }
    }

    public void setTakeThisManyTabletsAtaTime(int takeThisManyTabletsAtaTime) throws Exception{
        if(takeThisManyTabletsAtaTime < 1){
            Exception NumberOfTabletsIsLessThanOne = new Exception("Number of Tablets must be greater than zero");
            throw NumberOfTabletsIsLessThanOne;
        } else {
            _takeThisManyTabletsAtaTime = takeThisManyTabletsAtaTime;
        }
    }

    public void setMaxPillCountInBottle(int maxPillCountInBottle) throws Exception{
        if(maxPillCountInBottle < 1){
            Exception maxPillCountIsLessThanZero = new Exception("number of tablets that comes in bottle must be greater than zero");
            throw maxPillCountIsLessThanZero;
        } else {
            _maxPillCountInBottle = maxPillCountInBottle;
        }
    }

    public void setPillsRemainingInBottle(int pillsRemainingInBottle) throws Exception{
        if(pillsRemainingInBottle < 0){
            Exception pillCountIsLessThanZero = new Exception("Pills remaining can't be negative");
            throw pillCountIsLessThanZero;
        } else {
            _pillsRemainingInBottle = pillsRemainingInBottle;
        }
    }

    public void setRefillsRemaining(int refillsRemaining) throws Exception{
        if(refillsRemaining < 0){
            Exception refillCountIsLessThanZero = new Exception("number of refills can't be negative");
            System.out.println(refillCountIsLessThanZero.getMessage());
            System.out.println();
            throw refillCountIsLessThanZero;
        } else {
            _refillsRemaining = refillsRemaining;
        }
    }

    public void setPrescriptionName(String prescriptionName) throws Exception{

        Pattern pattern = Pattern.compile("[^A-Za-z]");
        Matcher matcher = pattern.matcher(prescriptionName);
        boolean containsBadCharacters = matcher.find();

        prescriptionName = prescriptionName.trim();

        if(prescriptionName.isEmpty()){
            Exception nullPrescriptionName = new Exception("Prescription Name must be filled out");
            throw nullPrescriptionName;
        }else if(prescriptionName.length() < 4) {
            Exception tooShortPrescriptionName = new Exception("Prescription Name must be at least four letters long");
            throw tooShortPrescriptionName;
        }else if(containsBadCharacters){
            Exception nonLetterPrescriptionName = new Exception("Prescription Name must only contain letters");
            throw nonLetterPrescriptionName;
        }else{
            _prescriptionName = prescriptionName;
        }
    }

    public void setPrescriptionExpiration(int year, int month, int day) throws Exception{
        try {
            LocalDate prescriptionExpiration = LocalDate.of(year, month, day);
            _prescriptionExpiration = prescriptionExpiration;
            _expirationYear = year;
            _expirationMonth = month;
            _expirationDay = day;
        } catch (Exception e){
            Exception invalidDate = new Exception("Invalid date");
            throw invalidDate;
        }
    }

    public void setDatePillCountAlertWasLastSent(LocalDate datePillCountAlertWasLastSent){
        _datePillCountAlertWasLastSent = datePillCountAlertWasLastSent;

        _pillCountAlertYear = datePillCountAlertWasLastSent.getYear();
        _pillCountAlertMonth = datePillCountAlertWasLastSent.getMonthValue();
        _pillCountAlertDay = datePillCountAlertWasLastSent.getDayOfMonth();
    }

    public void setDateExpirationAlertWasLastSent(LocalDate dateExpirationAlertWasLastSent){
        _dateExpirationAlertWasLastSent = dateExpirationAlertWasLastSent;

        _expirationAlertYear = dateExpirationAlertWasLastSent.getYear();
        _expirationAlertMonth = dateExpirationAlertWasLastSent.getMonthValue();
        _expirationAlertDay = dateExpirationAlertWasLastSent.getDayOfMonth();
    }

    public void setStartDate(LocalDateTime startDate){
        _startDate = startDate;
        _startDateYear = startDate.getYear();
        _startDateMonth = startDate.getMonthValue();
        _startDateDay = startDate.getDayOfMonth();
        _startDateHour = startDate.getHour();
        _startDateMinute = startDate.getMinute();
    }

    public boolean takePill(LocalDateTime timeYouTookPill){
        boolean actuallyTookPill = true;

        for(LocalDateTime takePillEvent: allTheTimesYouTookYourPills){
            if(timeYouTookPill.equals(takePillEvent)){
                actuallyTookPill = false;
            }
        }

        if(actuallyTookPill && getPillsRemainingInBottle() > 0){
            int pillsBeingTaken = getTakeThisManyTabletsAtaTime();
            int pillsInBottle = getPillsRemainingInBottle();
            int pillsLeft = pillsInBottle - pillsBeingTaken;

            if(pillsLeft < 0){
                pillsLeft = 0;
            }

            try {
                setPillsRemainingInBottle(pillsLeft);
            } catch (Exception e) {
                //shouldn't ever have an exception
            }

            allTheTimesYouTookYourPills.add(timeYouTookPill);
            backUpAllTheTimesYouTookYourPills();
            if(_startDate != null && timeYouTookPill.isBefore(_startDate)){
                setStartDate(timeYouTookPill);
            }

        } else{
            actuallyTookPill = false;
        }

        return actuallyTookPill;
    }

    public void deleteTimeYouTookPill(LocalDateTime timeYouWantDeleted){
        List<LocalDateTime> temp = new ArrayList<>();
        boolean didWeActuallyDelete = false;

        for(LocalDateTime timeYouTookPill: allTheTimesYouTookYourPills){
            if(timeYouTookPill.equals(timeYouWantDeleted)){
                didWeActuallyDelete = true;
            } else{
                temp.add(timeYouTookPill);
            }
        }

        if(didWeActuallyDelete){
            try {
                setPillsRemainingInBottle(getPillsRemainingInBottle() + getTakeThisManyTabletsAtaTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        allTheTimesYouTookYourPills = temp;
        stringsAllTheTimesYouTookYourPills = new ArrayList<>();

        backUpAllTheTimesYouTookYourPills();
    }

    public SingleMedicationPrescriptionHandler clone() {
        SingleMedicationPrescriptionHandler clone = new SingleMedicationPrescriptionHandler();

        try{
            clone.setStartDate(_startDate);
        }catch(Exception e){

        }

        clone.allTheTimesYouTookYourPills =getAllTheTimesYouTookYourPills();

        clone.stringsAllTheTimesYouTookYourPills = getStringsAllTheTimesYouTookYourPills();

        try {
            clone.setPrescriptionName(_prescriptionName);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        clone._expirationYear = _expirationYear;
        clone._expirationMonth = _expirationMonth;
        clone._expirationDay = _expirationDay;
        try {
            clone.setTakeThisManyTabletsAtaTime(_takeThisManyTabletsAtaTime);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            clone.setTakeMedicationThisManyTimesADay(_takeMedicationThisManyTimesADay);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            clone.setMilligramDosageInASingleTablet(_milligramDosageInASingleTablet);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            clone.setMaxPillCountInBottle(_maxPillCountInBottle);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            clone.setPillsRemainingInBottle(_pillsRemainingInBottle);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try{
            clone.setRefillsRemaining(_refillsRemaining);
        } catch(Exception e){

        }

        return clone;
    }

    public boolean isCloseToRunningOut(){
        if(_pillsRemainingInBottle == -1){
            _pillsRemainingInBottle = _maxPillCountInBottle;
        }

        int pillsLeftFiveDaysFromNow = _pillsRemainingInBottle - (_takeThisManyTabletsAtaTime * _takeMedicationThisManyTimesADay * 5);
        if(pillsLeftFiveDaysFromNow < 1){
            return true;
        }else {
            return false;
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

    public String sendPrescriptionExpirationAlert() {
        LocalDate today = LocalDate.now();
        Period timeLeft = Period.between(today, _prescriptionExpiration);
        PrescriptionExpirationAlert alert = new PrescriptionExpirationAlert(timeLeft, _prescriptionName);
        return alert.sendAlert();
    }

    public String sendLowPillCountAlert(){
        String message = _prescriptionName + " pill count is running low";
        if(_refillsRemaining == 0){
            message = message + " and you have no more refills";
        }
        return message;
    }

    public boolean haveNotAlreadySentPillCountAlertToday(){
        if(_datePillCountAlertWasLastSent == null){
            return true;
        } else {
            Period allZeros = Period.ZERO;
            Period timeSinceLastAlert = Period.between(LocalDate.now(), _datePillCountAlertWasLastSent);
            if(timeSinceLastAlert.equals(allZeros)){
                return false;
            } else{
                return true;
            }
        }
    }

    public boolean haveNotAlreadySentExpirationAlertToday(){
        if(_dateExpirationAlertWasLastSent == null){
            return true;
        } else {
            Period allZeros = Period.ZERO;
            Period timeSinceLastAlert = Period.between(LocalDate.now(), _dateExpirationAlertWasLastSent);
            if(timeSinceLastAlert.equals(allZeros)){
                return false;
            } else{
                return true;
            }
        }
    }

    public boolean isValid() throws Exception {
        boolean isValid = false;

        if(_prescriptionExpiration == null){
            try {
                setPrescriptionExpiration(_expirationYear, _expirationMonth, _expirationDay);
            } catch(Exception e){

            }
        }

        if(     (_prescriptionName != null) &&
                (_prescriptionExpiration != null) &&
                (_takeThisManyTabletsAtaTime != 0) &&
                (_milligramDosageInASingleTablet != 0) &&
                (_takeMedicationThisManyTimesADay != 0) &&
                (_maxPillCountInBottle != -1)
        ){
            if(_pillsRemainingInBottle == -1){
                _pillsRemainingInBottle = _maxPillCountInBottle;
            }
            isValid = true;
        } else if(_prescriptionName == null){
            Exception nullName = new Exception("Must enter prescription name");
            throw nullName;
        } else if(_milligramDosageInASingleTablet == 0){
            Exception enterDosage = new Exception("Must enter milligram dosage in a single tablet");
            throw enterDosage;
        } else if(_takeThisManyTabletsAtaTime == 0){
            Exception enterNumTablets = new Exception("Must enter number of tablets to take");
            throw enterNumTablets;
        } else if(_takeMedicationThisManyTimesADay == 0){
            Exception enterNumTimesADay = new Exception("Must enter number of times a day to take medication");
            throw enterNumTimesADay;
        } else if(_maxPillCountInBottle == -1){
            Exception enterTabletsRemaining = new Exception("Must enter number of tablets that come in bottle");
            throw enterTabletsRemaining;
        } else if(_prescriptionExpiration == null) {
            Exception nullExpiration = new Exception("Must enter prescription expiration");
            throw nullExpiration;
        }

        return isValid;
    }

}

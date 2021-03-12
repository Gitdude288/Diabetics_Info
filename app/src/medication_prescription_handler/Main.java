package medication_prescription_handler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;


public class Main {

    public static List<SingleMedicationPrescriptionHandler> generateSingleHandlerList(){
        List<SingleMedicationPrescriptionHandler> handlerList = new ArrayList<>();

        for(int i = -1; i < 11; i+= 2)
        {
            LocalDate prescriptionExpiration = LocalDate.now();
            prescriptionExpiration = prescriptionExpiration.plusDays(i);

            int year = prescriptionExpiration.getYear();
            int month = prescriptionExpiration.getMonthValue();
            int day = prescriptionExpiration.getDayOfMonth();

            SingleMedicationPrescriptionHandler tempHandler = new SingleMedicationPrescriptionHandler();
            tempHandler.setPrescriptionName("med " + i);
            try {
                tempHandler.setPrescriptionExpiration(year, month, day);
            } catch (Exception e) {
                e.printStackTrace();
            }
            handlerList.add(tempHandler);
        }

        return handlerList;
    }

    public static void main(String[] args) {

        //Testing PrescriptionExpirationAlert
        System.out.println("Testing PrescriptionExpirationAlert-------------------------------------------------");
        System.out.println();

        LocalDate today = LocalDate.now();
        LocalDate alertDate = LocalDate.now();
        alertDate = alertDate.plusYears(1);
        alertDate = alertDate.plusMonths(1);
        alertDate = alertDate.plusDays(1);

        Period timeLeft = Period.between(today, alertDate);

        PrescriptionExpirationAlert testExpirationAlert1 = new PrescriptionExpirationAlert(timeLeft,"test1");
        assertTrue( "Incorrect message from test1",testExpirationAlert1.sendAlert().equals("Your prescription for test1 expires in 1 year, 1 month, 1 day"));

        alertDate = LocalDate.now();
        alertDate = alertDate.plusYears(2);
        alertDate = alertDate.plusMonths(2);
        alertDate = alertDate.plusDays(2);

        timeLeft = Period.between(today, alertDate);

        PrescriptionExpirationAlert testExpirationAlert2 = new PrescriptionExpirationAlert(timeLeft,"test2");
        assertTrue(  "Incorrect message from test2",testExpirationAlert2.sendAlert().equals("Your prescription for test2 expires in 2 years, 2 months, 2 days"));

        alertDate = LocalDate.now();
        alertDate = alertDate.minusYears(1);
        alertDate = alertDate.minusMonths(1);
        alertDate = alertDate.minusDays(1);

        timeLeft = Period.between(today, alertDate);

        PrescriptionExpirationAlert testExpirationAlert3 = new PrescriptionExpirationAlert(timeLeft,"test3");
        assertTrue( "Incorrect message from test3",testExpirationAlert3.sendAlert().equals("Your prescription for test3 is overdue by 1 year, 1 month, 1 day!"));

        alertDate = LocalDate.now();
        alertDate = alertDate.minusYears(2);
        alertDate = alertDate.minusMonths(2);
        alertDate = alertDate.minusDays(2);

        timeLeft = Period.between(today, alertDate);

        PrescriptionExpirationAlert testExpirationAlert4 = new PrescriptionExpirationAlert(timeLeft,"test4");
        assertTrue( "Incorrect message from test4",testExpirationAlert4.sendAlert().equals("Your prescription for test4 is overdue by 2 years, 2 months, 2 days!"));


        //Testing invalid input for handler
        System.out.println("Testing invalid input for handler---------------------------------------------------");
        System.out.println();

        SingleMedicationPrescriptionHandler testHandler = new SingleMedicationPrescriptionHandler();

        //adding bad date
        try {
            testHandler.setPrescriptionExpiration(2021, 3, 33);
        } catch (Exception e) {
            assertTrue("expected different error message for adding bad date",e.getMessage().equals("Invalid date"));
        }
        assertTrue(testHandler.getPrescriptionExpiration() == null);

        List<SingleMedicationPrescriptionHandler> list = generateSingleHandlerList();

        MedicationPrescriptionGeneralHandler generalHandler = new MedicationPrescriptionGeneralHandler(list);
        generalHandler.run();

    }
}

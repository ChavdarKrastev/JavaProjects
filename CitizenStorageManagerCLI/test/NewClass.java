
import DAL.MysqlEducationStorage;
import DAL.MysqlInsuranceStorage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user1
 */
public class NewClass {
    
    public boolean checkSocialInsurance(int personID) {
        boolean hasRights = false;
        boolean period = false;
        boolean secondaryEduc = false;

        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

        String month = Integer.toString(new MysqlInsuranceStorage().getInsurance(personID).get(0).getMonth());
        String year = Integer.toString(new MysqlInsuranceStorage().getInsurance(personID).get(0).getYear());

        String monthYear = ("28." + month + "." + year);

        date = LocalDate.parse(monthYear, formatter);
        if (date.isBefore(LocalDate.now().minusMonths(3))) {
            period = true;
        }
        
        //new MysqlEducationStorage().getEducation(personID).size()
        if (personID > 1) {
            secondaryEduc = true;
        }
        if (period && secondaryEduc) {
            hasRights = true;

        }
        return hasRights;
    }

    
    public static void main(String[] args)
    {
        
      System.out.println(new NewClass().checkSocialInsurance(7));
        
    }


    }


package DAL;

import Contracts.SocialInsuranceContract;
import address.Address;
import education.Education;
import education.GradedEducation;
import education.HigherEducation;
import insurance.SocialInsuranceRecord;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import personaldetails.Citizen;

public class MysqlInsuranceStorage implements SocialInsuranceContract {

    public ArrayList<SocialInsuranceRecord> getInsurance(int id) {
        SocialInsuranceRecord sir = null;
        ArrayList<SocialInsuranceRecord> arrSir = new ArrayList();
        //ResultSet rs = null;

        String sqlquery = "SELECT * FROM personcharacteristics.insurance "
                + "where personID = ?";

        try (Connection con = DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
                PreparedStatement statement = con.prepareStatement(sqlquery)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {

                    sir = new SocialInsuranceRecord(rs.getInt(2), rs.getInt(3), rs.getDouble(4));
                    arrSir.add(sir);

                }
            } catch (SQLException ex) {

                while (ex != null) {
                    System.out.println(ex.getSQLState());
                    System.out.println(ex.getMessage());
                    System.out.println(ex.getErrorCode());
                    ex = ex.getNextException();
                }

            }
            return arrSir;
        } catch (SQLException ex) {
            Logger.getLogger(MysqlInsuranceStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrSir;
    }

    public void insertInsurance(SocialInsuranceRecord record, int personID) {
        String sqlquery = "INSERT INTO personcharacteristics.insurance "
                + "(year, month, amount, personID)"
                + "VALUES (?,?,?,?)";

        try (Connection con = DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
                PreparedStatement statement = con.prepareStatement(sqlquery)) {

            statement.setInt(1, record.getYear());

            statement.setInt(2, record.getMonth());

            statement.setDouble(3, record.getAmount());
            statement.setInt(4, personID);

            //statement.registerOutParameter(5, java.sql.Types.INTEGER);
            statement.execute();

            // i = statement.getInt("new_id");
        } catch (SQLException ex) {

            while (ex != null) {
                System.out.println(ex.getSQLState());
                System.out.println(ex.getMessage());
                System.out.println(ex.getErrorCode());
                ex = ex.getNextException();
            }

        }

    }

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
        if (new MysqlEducationStorage().getEducation(personID).size() > 1) {
            secondaryEduc = true;
        }
        if (period && secondaryEduc) {
            hasRights = true;

        }
        return hasRights;
    }

    public double checkSocialAmount(int personID) {
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        String month;
        String year;
        String monthYear;
        double sum = 0.0;
        int count=0;
        double average = 0.0;

        ArrayList<SocialInsuranceRecord> socialRecords = new ArrayList();
        socialRecords = new MysqlInsuranceStorage().getInsurance(personID);
        for (int i = 0; i < socialRecords.size(); i++) {
            month = Integer.toString(socialRecords.get(i).getMonth());
            year = Integer.toString(socialRecords.get(i).getYear());
            monthYear = ("28." + month + "." + year);
            date = LocalDate.parse(monthYear, formatter);
            
            if(date.isAfter(LocalDate.now().minusMonths(27))) {
                    sum+=socialRecords.get(i).getAmount();
                    count++;
            }
        }
        average = sum/count;
        average = Math.round(average*100)/100;
        return average;
    }

    static final String DBMS_CONN_STRING = "jdbc:mysql://localhost:3306/personcharacteristics?zeroDateTimeBehavior=convertToNull";
    static final String DBMS_USERNAME = "root";
    static final String DBMS_PASSWORD = "123456";

    public static void main(String[] args) throws SQLException {

//        MysqlEducationStorage e1 = new MysqlEducationStorage();
//        List<Education> educations = e1.getEducation(1);
//        for (int i = 0; i < educations.size(); i++) {
//
//            if (educations.get(i) instanceof HigherEducation) {
//
//                float fl = ((HigherEducation) educations.get(i)).getFinalGrade();
//                System.out.println(e1.getEducation(1).get(i).getDegree() + " " + e1.getEducation(1).get(i).getInstitutionName()
//                        + " " + e1.getEducation(1).get(i).getGraduationDate() + " " + fl);
//            } else {
//                System.out.println(e1.getEducation(1).get(i).getDegree() + " " + e1.getEducation(1).get(i).getInstitutionName()
//                        + " " + e1.getEducation(1).get(i).getGraduationDate());
//            }
//
//        }
        String month = Integer.toString(new MysqlInsuranceStorage().getInsurance(2).get(0).getMonth());
        String year = Integer.toString(new MysqlInsuranceStorage().getInsurance(2).get(0).getYear());

        String monthYear = ("28." + month + "." + year);

        System.out.println(monthYear);

//        ArrayList<Address> addres = MysqlAddressStorage.getAddress(2);
//        System.out.println(addres.get(0).getCity());
//MysqlPersonStorage p1 = new MysqlPersonStorage();
//p1.truncateTables();
//MysqlPersonStorage p1 = new MysqlPersonStorage();
//
//int pid = p1.insertPerson("Gosho", "Petrov", "Todov", "M", "177", "21.2.1998");
//System.out.println(pid);
//
//MysqlEducationStorage e1 = new MysqlEducationStorage();
//e1.insertEducation("S","12.09.1990", "12.04.1995","SOU", "5.59", 1);
//
//insertInsurance(2016,10,122.172,pid);
    }
}

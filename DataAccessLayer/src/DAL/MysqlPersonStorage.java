package DAL;

import Contracts.PersonContract;
import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import personaldetails.Citizen;
import personaldetails.Gender;

public class MysqlPersonStorage implements PersonContract{

    static final String DBMS_CONN_STRING = "jdbc:mysql://localhost:3306/personcharacteristics?zeroDateTimeBehavior=convertToNull";
    static final String DBMS_USERNAME = "root";
    static final String DBMS_PASSWORD = "123456";

    String person = "Пламен;Стойчев;Измирлиев;M;16.7.1980;206;България;София;Студентски;1016;Опълченска;21;;;P;СОУ Уилям Гладстон;15.9.1986;15.6.1993;S;СОУ Уилям Гладстон;15.9.1993;30.6.1998;3.343;B;СОУ Уилям Гладстон;1.10.1999;1.6.2003;3.045\n"
            + "2016;11;1015.20;2016;10;605.93;2016;9;701.61;2016;7;981.86;2016;4;1042.57;2016;3;919.87;2016;2;720.00;2015;12;969.75;2015;6;641.16;2015;3;811.76;2015;2;757.07;2014;12;1321.31;2014;11;863.39;2014;9;1539.51;2014;7;1159.62;2014;5;1295.59;2014;3;910.59;2014;1;1033.80";

    String[] deatils = person.split(";");

    public Citizen getPersonDetails(int id) {
        Citizen person = null;
        //ResultSet rs = null;

        String sqlquery = "SELECT * FROM personcharacteristics.person "
                + "where id = ?";

        try (Connection con = DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
                PreparedStatement statement = con.prepareStatement(sqlquery)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    final LocalDate date = rs.getDate(7).toLocalDate();
                    Gender gender;
                    if (rs.getString(5).equals("M")) {
                        gender = Gender.valueOf("Male");
                    } else {
                        gender = Gender.valueOf("Female");
                    }

                    person = new Citizen(rs.getString(2), rs.getString(3), rs.getString(4), gender, rs.getInt(6), date);
                }
            }
        } catch (SQLException ex) {

            while (ex != null) {
                System.out.println(ex.getSQLState());
                System.out.println(ex.getMessage());
                System.out.println(ex.getErrorCode());
                ex = ex.getNextException();
            }

        }
        return person;
    }

    public void truncateTables() {
        try (Connection conn = DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
                CallableStatement statement = conn.prepareCall("{call trucateTables()}")) {
            statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(MysqlPersonStorage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int insertPerson(Citizen citizen) {
        int personID = 0;
        //Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
                CallableStatement statement = conn.prepareCall("{call insert_person2(?, ?, ?, ?, ?, ?, ?)}")) {

            statement.setString(1, citizen.getFirstName());
            statement.setString(2, citizen.getMiddleName());
            statement.setString(3, citizen.getLastName());
            String gen = citizen.getGender().name();
            statement.setString(4, gen.trim());
            statement.setInt(5, citizen.getHeight());
            statement.setDate(6, java.sql.Date.valueOf(citizen.getDateOfBirth()));
            statement.registerOutParameter(7, java.sql.Types.INTEGER);
            statement.executeQuery();

            personID = statement.getInt("new_id");

        } catch (SQLException ex) {

            while (ex != null) {
                System.out.println(ex.getSQLState());
                System.out.println(ex.getMessage());
                System.out.println(ex.getErrorCode());
                ex = ex.getNextException();
            }
        }
        return personID;
    }
}

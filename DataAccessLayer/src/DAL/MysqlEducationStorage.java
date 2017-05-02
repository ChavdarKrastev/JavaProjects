package DAL;

import Contracts.EducationContract;
import static DAL.MysqlInsuranceStorage.DBMS_CONN_STRING;
import education.Education;
import education.EducationDegree;
import education.GradedEducation;
import education.HigherEducation;
import education.PrimaryEducation;
import education.SecondaryEducation;
import insurance.SocialInsuranceRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlEducationStorage implements EducationContract{

    static final String DBMS_CONN_STRING = "jdbc:mysql://localhost:3306/personcharacteristics?zeroDateTimeBehavior=convertToNull";
    static final String DBMS_USERNAME = "root";
    static final String DBMS_PASSWORD = "123456";

    public ArrayList<Education> getEducation(int id) {
        //Education education = null;
        ArrayList<Education> educations = new ArrayList();
        

        String sqlquery = "SELECT * FROM personcharacteristics.education "
                + "where personID = ?";

        try (Connection con = DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
                PreparedStatement statement = con.prepareStatement(sqlquery)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {

                    final LocalDate enroll = rs.getDate(3).toLocalDate();
                    final LocalDate graduate = rs.getDate(4).toLocalDate();

                    if (rs.getString(7).equals("Primary")) {
                        PrimaryEducation educationP = new PrimaryEducation(rs.getString(5), enroll, graduate);
                        educations.add(educationP);
                    } else if (rs.getString(7).equals("Secondary")) {
                        SecondaryEducation educationS = new SecondaryEducation(rs.getString(5), enroll, graduate);
                        educations.add(educationS);
                    } else {
                        EducationDegree degree = null;
                        if (rs.getString(7).equals("Bachelor")) {
                            degree = EducationDegree.valueOf("Bachelor");
                        } else if (rs.getString(7).equals("Master")) {
                            degree = EducationDegree.valueOf("Master");
                        } else if (rs.getString(7).equals("Doctorate")) {
                            degree = EducationDegree.valueOf("Doctorate");
                        }
                        HigherEducation educationH = new HigherEducation(rs.getString(5), enroll, graduate, degree);
                        if(graduate.isBefore(LocalDate.now()))
                        {
                        educationH.gotGraduated(rs.getFloat(6));
                        }
                        educations.add(educationH);
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
        } catch (SQLException ex) {
            Logger.getLogger(MysqlEducationStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return educations;
    }

    public void insertEducation(Education education, int PersonID) {
        String sqlquery = "INSERT INTO personcharacteristics.education "
                + "(enrollmentDate, graduationDate, institutionName, finalGrade, degree, PersonID)"
                + "VALUES (?,?,?,?,?,?)";

        try (Connection con = DriverManager.getConnection(DBMS_CONN_STRING, DBMS_USERNAME, DBMS_PASSWORD);
                PreparedStatement statement = con.prepareStatement(sqlquery)) {

            //String degree = ((EducationDegree)education.getDegree()).name();
            //statement.setString(1, degree);
            statement.setDate(1, java.sql.Date.valueOf(education.getEnrollmentDate()));
            statement.setDate(2, java.sql.Date.valueOf(education.getGraduationDate()));
            statement.setString(3, education.getInstitutionName());
            
            if((!education.getDegree().name().equalsIgnoreCase("Primary"))&&(education.getGraduationDate().isBefore(LocalDate.now())))
            {
            statement.setFloat(4, ((GradedEducation)education).getFinalGrade());
            }
            else
            {
                statement.setFloat(4, 0.0f);
            }
            statement.setString(5, education.getDegree().name());
            
            statement.setInt(6, PersonID);

           statement.execute();

        } catch (SQLException ex) {

            while (ex != null) {
                System.out.println(ex.getSQLState());
                System.out.println(ex.getMessage());
                System.out.println(ex.getErrorCode());
                ex = ex.getNextException();
            }
        }
    }
}

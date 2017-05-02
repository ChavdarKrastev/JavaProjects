package DataAccess;

import DAL.MysqlAddressStorage;
import DAL.MysqlEducationStorage;
import DAL.MysqlPersonStorage;
import DAL.MysqlInsuranceStorage;
import address.Address;
import education.Education;
import education.GradedEducation;
import education.HigherEducation;
import education.SecondaryEducation;
import insurance.SocialInsuranceRecord;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personaldetails.Citizen;

/**
 *
 * @author user1
 */
public class FindUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        String strId = (String)request.getParameter("personID");
                
        String pidFromCSI = (String)request.getAttribute("pers");
        
        //strId.equals(null) doesnt't work
        if (strId==null)
        {
            strId = pidFromCSI;
        }
        
        int intId = Integer.parseInt(strId);
        Class.forName("com.mysql.jdbc.Driver");
        
        MysqlPersonStorage person2 = new MysqlPersonStorage();
        Citizen citizen = person2.getPersonDetails(intId);

        request.setAttribute("firstName", citizen.getFirstName());
        request.setAttribute("middleName", citizen.getMiddleName());
        request.setAttribute("lastName", citizen.getLastName());
        request.setAttribute("gender", citizen.getGender());
        request.setAttribute("birthDate", citizen.getDateOfBirth());
        request.setAttribute("height", citizen.getHeight());

        MysqlAddressStorage address2 = new MysqlAddressStorage(); 
        ArrayList<Address> addresses = address2.getAddress(intId);
        int adrNumber = addresses.size();
        String appendedAdr = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < adrNumber; i++) {

            sb.append(addresses.get(i).getCountry() + "; ");
            sb.append(addresses.get(i).getCity() + "; ");
            sb.append(addresses.get(i).getMunicipality() + "; ");
            sb.append(addresses.get(i).getPostalCode() + "; ");
            sb.append(addresses.get(i).getStreet() + "; ");
            sb.append(addresses.get(i).getNumber() + "; ");
            sb.append(addresses.get(i).getFloor() + "; ");
            sb.append(addresses.get(i).getApartmentNo() + "; ");

            appendedAdr = sb.toString();
        }

        request.setAttribute("addresses", appendedAdr);

        MysqlEducationStorage education2 = new MysqlEducationStorage();
        
        ArrayList<Education> educations = education2.getEducation(intId);
        String appendedEdu = "";
        int eduNumber = educations.size();
        sb.setLength(0);
        for (int i = 0; i < eduNumber; i++) {

            if (((educations.get(i) instanceof HigherEducation)||(educations.get(i) instanceof SecondaryEducation)) 
                    && (educations.get(i).isGraduated())) {
                
                sb.append(educations.get(i).getDegree().name() + "; ");
                sb.append(educations.get(i).getInstitutionName() + "; ");
                sb.append(educations.get(i).getEnrollmentDate() + "; ");
                sb.append(educations.get(i).getGraduationDate() + "; ");
                sb.append(((GradedEducation)educations.get(i)).getFinalGrade() + "; ");

                appendedEdu = sb.toString();
            }
            else
            {
                sb.append(educations.get(i).getDegree().name() + "; ");
                sb.append(educations.get(i).getInstitutionName() + "; ");
                sb.append(educations.get(i).getEnrollmentDate() + "; ");
                sb.append(educations.get(i).getGraduationDate() + "; ");
                
                appendedEdu = sb.toString();
            }
        }
        request.setAttribute("educations", appendedEdu);
        
        MysqlInsuranceStorage insurance2 = new MysqlInsuranceStorage();
        ArrayList<SocialInsuranceRecord> ins = insurance2.getInsurance(intId);
        String appendedIns = "";
        int insNumber = ins.size();
        sb.setLength(0);
        for (int i = 0; i < insNumber; i++) {

            sb.append(ins.get(i).getYear() + "; ");
            sb.append(ins.get(i).getMonth() + "; ");
            sb.append(ins.get(i).getAmount() + "; ");
           
            appendedIns = sb.toString();
        }
        request.setAttribute("insRecords", appendedIns);
        
        request.setAttribute("personid", strId);
        
        request.getRequestDispatcher("/userInfo.jsp").forward(request, response);
        
//         request.setAttribute("personID",strId);
//         request.getRequestDispatcher("AddEducation").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FindUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FindUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FindUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FindUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

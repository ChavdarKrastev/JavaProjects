/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import DAL.MysqlEducationStorage;
import education.Education;
import education.EducationDegree;
import education.GradedEducation;
import education.HigherEducation;
import education.PrimaryEducation;
import education.SecondaryEducation;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user1
 */
public class AddEducation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        MysqlEducationStorage addEducation = new MysqlEducationStorage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        LocalDate date;
        String strID = (String) request.getParameter("inputName");
        
        request.setAttribute("personid", strID);
        
        int personID = Integer.parseInt(strID);
        
        request.getRequestDispatcher("/newEducation.jsp").forward(request, response);

        String educationStr = request.getParameter("newEducation");

        String[] educationElements = educationStr.split(";");
        
        Education education = null;

       switch (educationElements[0])
       {
        
           case "P":
            education = new PrimaryEducation((String)educationElements[1].trim(), LocalDate.parse(educationElements[2], formatter), LocalDate.parse(educationElements[3], formatter));
            addEducation.insertEducation(education, personID);
        break;
       case "S":
            education = new SecondaryEducation((String)educationElements[1], LocalDate.parse(educationElements[2], formatter), LocalDate.parse(educationElements[3], formatter));
           
            if (LocalDate.parse(educationElements[3], formatter).isBefore(LocalDate.now())) {
                ((GradedEducation) education).gotGraduated(Float.parseFloat(educationElements[4]));
                addEducation.insertEducation(education, personID);
            } else {
                addEducation.insertEducation(education, personID);
            }
        break;
        case "B":
     
            education = new HigherEducation((String)educationElements[1], LocalDate.parse(educationElements[2], formatter), LocalDate.parse(educationElements[3], formatter), EducationDegree.valueOf("Bachelor"));
            if (LocalDate.parse(educationElements[3], formatter).isBefore(LocalDate.now())) {
                ((GradedEducation) education).gotGraduated(Float.parseFloat(educationElements[4]));
                addEducation.insertEducation(education, personID);
            } else {
                addEducation.insertEducation(education, personID);
            }
            break;
            
        case "M":
            education = new HigherEducation((String)educationElements[1], LocalDate.parse(educationElements[2], formatter), LocalDate.parse(educationElements[3], formatter), EducationDegree.valueOf("Master"));
            if (LocalDate.parse(educationElements[3], formatter).isBefore(LocalDate.now())) {
                ((GradedEducation) education).gotGraduated(Float.parseFloat(educationElements[4]));
                addEducation.insertEducation(education, personID);
            } else {
                addEducation.insertEducation(education, personID);
            }
            break;
            
        case "D":
            education = new HigherEducation((String)educationElements[1], LocalDate.parse(educationElements[2], formatter), LocalDate.parse(educationElements[3], formatter), EducationDegree.valueOf("Doctorate"));
            if (LocalDate.parse(educationElements[3], formatter).isBefore(LocalDate.now())) {
                ((GradedEducation) education).gotGraduated(Float.parseFloat(educationElements[4]));
                addEducation.insertEducation(education, personID);
            } else {
                addEducation.insertEducation(education, personID);
            }
            break;
        }
       //addEducation.insertEducation(education, personID);
       //request.setAttribute("institute", educationElements[1]);
       request.getRequestDispatcher("/newEducation.jsp").forward(request, response);
       
       
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
        processRequest(request, response);
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
        processRequest(request, response);
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

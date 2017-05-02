/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import DAL.MysqlInsuranceStorage;
import insurance.SocialInsuranceRecord;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user1
 */
public class addSocialInsuranceRecord extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String strID = (String) request.getParameter("inputName");
        request.setAttribute("personid", strID);
        int personID = Integer.parseInt(strID);
        request.getRequestDispatcher("/newSocialInsuranceRecord.jsp").forward(request, response);
        
        MysqlInsuranceStorage addInsurance = new MysqlInsuranceStorage();
        
        String insuarnceStr = request.getParameter("newInsurance");
        String[] insuranceElements = insuarnceStr.split(";");
        
        SocialInsuranceRecord insurance = new SocialInsuranceRecord(Integer.parseInt(insuranceElements[0]),Integer.parseInt(insuranceElements[1]), Double.parseDouble(insuranceElements[2]));
        
        addInsurance.insertInsurance(insurance, personID);
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

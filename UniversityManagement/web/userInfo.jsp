<%-- 
    Document   : userInfo
    Created on : Nov 30, 2016, 11:30:35 PM
    Author     : user1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Info</title>
    </head>
    <body>
        <form action="FindUser" method="post"> 
            personID: <input name="personID">  
            <input type="submit" value="Find"></p>
        </form> 

        <%
            Object value = request.getAttribute("firstName");
            Object value2 = request.getAttribute("middleName");
            Object value3 = request.getAttribute("lastName");
            Object value4 = request.getAttribute("gender");
            Object value5 = request.getAttribute("birthDate");
            Object value6 = request.getAttribute("height");

            Object addresses = request.getAttribute("addresses");
            Object educations = request.getAttribute("educations");

            Object insRecords = request.getAttribute("insRecords");
        %>



        <p><%=value + "; " + value2 + "; " + value3 + "; " + value4 + "; " + value5 + "; " + value6 + "; " + addresses + "; " + educations%></p>

        <p><%=insRecords%></p>

        <% Object pid = request.getAttribute("personid");%>

        <p><%=pid%></p>



        <form action="AddEducation" method="post">
            <input type="hidden" id="thisField" name="inputName" value=<%=pid%>>
            <button type="submit" name="button" value="addEduc">New Education</button>
        </form>
        <p> </p>
        <form action="CheckSocialInsurance" method="post">
            <input type="hidden" id="thisField" name="inputName" value=<%=pid%>>
            <button type="submit" name="button" value="addEduc">Check Social Insurance</button>
        </form>

        <% Object rights = request.getAttribute("hasrights"); %>
        <% Object amount = request.getAttribute("averageAmount"); %>
        <% Object text = request.getAttribute("textSocial"); %>


        <% if (rights == null) {  %>
        <p>  </p>
        <%} else {%>
        <p> <%= rights%> </p>
        <%}%>

        <% if (amount == null) {  %>
        <p>  </p>
        <%} else {%>
        <p> <%=text%> <%= amount%> </p>
        <%}%>

        <form action="addSocialInsuranceRecord" method="post">
            <input type="hidden" id="thisField" name="inputName" value=<%=pid%>>
            <button type="submit" name="button" value="socialRecord">New Social Insurance Record</button>
        </form>




    </body>
</html>

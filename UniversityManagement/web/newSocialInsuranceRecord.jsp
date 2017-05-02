<%-- 
    Document   : newSocialInsuranceRecord
    Created on : Dec 4, 2016, 10:06:00 PM
    Author     : user1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Social Insurance record</h1>

        <% Object pid = request.getAttribute("personid");%>
        <form action="addSocialInsuranceRecord" method="post"> 
            Enter Education: <input name="newInsurance">  
            <input type="hidden" id="thisField" name="inputName" value=<%=pid%>>
            <input type="submit" value="Enter S.I.Record"></p>
        </form> 

            <p></p>
            
        <form action="FindUser" method="post"> 

            <input type="hidden" id="thisField" name="personID" value=<%=pid%>>
            <input type="submit" value="Back to Person Details"></p>
        </form> 
    </body>
</html>

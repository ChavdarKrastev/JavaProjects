<%-- 
    Document   : newEducation
    Created on : Dec 2, 2016, 11:39:29 PM
    Author     : user1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Education</title>
    </head>
    <body>
        <% Object pid = request.getAttribute("personid"); %>

       <form action="AddEducation" method="post"> 
Enter Education: <input name="newEducation">  
<input type="hidden" id="thisField" name="inputName" value=<%=pid%>>
<input type="submit" value="Enter Education"></p>
</form> 
        
        <% //Object inst = request.getAttribute("institute"); %> 


<form action="FindUser" method="post"> 
 
<input type="hidden" id="thisField" name="personID" value=<%=pid%>>
<input type="submit" value="Back to Person"></p>
</form> 
    </body>
</html>

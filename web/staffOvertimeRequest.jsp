<%-- 
    Document   : staffOvertimeRequest
    Created on : 3 Jun 2025, 11:34:05 pm
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="OvertimeServlet" method="post">
            <label>Date: <input type="date" name="otDate" required></label><br>
            <label>Start Time: <input type="time" name="startTime" required></label><br>
            <label>End Time: <input type="time" name="endTime" required></label><br>
            <label>Reason:<br><textarea name="reason" rows="3" required></textarea></label><br>
            <input type="submit" value="Submit Request">
        </form>
    </body>
</html>

<%-- 
    Document   : employeeSuccess
    Created on : 20 Jan 2025, 1:53:23?am
    Author     : user
--%>

<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Retrieve the success message from the session
    String message = (String) session.getAttribute("message");
    session.removeAttribute("message"); // Clear the message after displaying
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f4f4f9;
        }
        .message-box {
            background-color: #28a745;
            color: white;
            padding: 20px;
            border-radius: 5px;
            font-size: 18px;
            text-align: center;
        }
        .message-box a {
            color: white;
            text-decoration: underline;
            margin-top: 10px;
            display: inline-block;
        }
    </style>
</head>
<body>
    <div class="message-box">
        <p><%= message != null ? message : "Operation completed successfully!" %></p>
        <a href="EmployeeListServlet">Go to Employee List</a>
    </div>
</body>
</html>


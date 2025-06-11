<%-- 
    Document   : foPayrunList
    Created on : 18 May 2025, 5:21:27 am
    Author     : user
--%>

<%@ page import="java.util.*, model.Payrun" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Retrieve FO session attributes
    String userID = (String) session.getAttribute("userID");
    String username = (String) session.getAttribute("username");
    String fullname = (String) session.getAttribute("fullname");

    // Redirect to login if username is missing
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>CC | Pay Run List</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            display: flex;
            height: 100vh;
            background-color: #f4f4f9;
        }
        .container {
            display: flex;
            width: 100%;
        }

        /* Sidebar Styling */
        .sidebar {
            width: 250px;
            background-color: #284b63; /* Different blue shade for FO */
            color: white;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
        }

        .profile {
            text-align: center;
            margin-bottom: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .profile-pic {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            background-color: #a8c0d9;
            margin-bottom: 10px;
        }

        .profile-name {
            font-size: 18px;
            margin: 0;
            font-weight: bold;
            text-align: center;
        }

        .nav-links {
            list-style: none;
            padding: 0;
            width: 100%;
        }

        .nav-links li {
            width: 100%;
            margin: 10px 0;
        }

        .nav-links a {
            text-decoration: none;
            color: white;
            padding: 10px 15px;
            display: block;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .nav-links a:hover,
        .nav-links a.active {
            background-color: #416a8c;
        }

        .logout {
            margin-top: auto;
            padding: 10px 15px;
            background-color: #416a8c;
            color: white;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            width: 100%;
            text-decoration: none;
        }

        .logout:hover {
            background-color: #5a87b0;
        }

        /* Main Content Styling */
        .content {
            flex-grow: 1;
            padding: 30px;
        }

        .header {
            margin-bottom: 20px;
        }

        .header h1 {
            font-size: 24px;
            color: #284b63;
        }

        .header .name {
            color: #416a8c;
        }

        /* Dashboard Sections */
        .section {
            background-color: #e1e1e1;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .section h2 {
            font-size: 18px;
            color: #284b63;
            margin: 0;
        }
        
        .section h3 {
            font-size: 16px;
            color: #284b63;
            margin: 0;
        }

        .section p {
            margin: 5px 0;
            font-size: 16px;
            color: #333;
        }

        .section button {
            padding: 10px 20px;
            font-size: 14px;
            color: #fff;
            background-color: #416a8c;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .section button:hover {
            background-color: #5a87b0;
        }

        .chart-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .chart {
            width: 80px;
            height: 80px;
            background-color: #a8c0d9;
            border-radius: 50%;
            margin-right: 20px;
        }

        .chart-label {
            font-size: 16px;
            color: #333;
        }

        .employee-count {
            text-align: center;
            font-size: 20px;
            color: #284b63;
            font-weight: bold;
        }

        /* Dropdown styles from HR */
        .dropdown {
            position: relative;
            display: block;
        }

        .dropbtn {
            text-decoration: none;
            color: white;
            padding: 10px 15px;
            display: block;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #416a8c;
            min-width: 200px;
            z-index: 1;
            top: 100%;
            left: 0;
            border-radius: 5px;
        }

        .dropdown-content a {
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            display: block;
        }

        .dropdown-content a:hover {
            background-color: #5a87b0;
        }

        .dropdown:hover .dropdown-content {
            display: block;
        }
    </style>
</head>
<body>
    <h2>Pay Run List</h2>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>Month</th>
            <th>Year</th>
            <th>Created Date</th>
            <th>Created By</th>
            <th>Status</th>
            <th>Total Salary (RM)</th>
            <th>Action</th>
        </tr>
        <%
            List<Payrun> payrunList = (List<Payrun>) request.getAttribute("payrunList");
            for (Payrun pr : payrunList) {
        %>
        <tr>
            <td><%= pr.getPayrunID() %></td>
            <td><%= pr.getMonth() %></td>
            <td><%= pr.getYear() %></td>
            <td><%= pr.getCreatedDate() %></td>
            <td><%= pr.getCreatedBy() %></td>
            <td><%= pr.getStatus() %></td>
            <td><%= String.format("%.2f", pr.getTotalSalary()) %></td>
            <td>
                <a href="PayrunServlet?payrunID=<%= pr.getPayrunID() %>">View</a>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>

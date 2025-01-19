<%-- 
    Document   : foEmpSalaryDetails
    Created on : 17 Jan 2025, 1:31:18 am
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Retrieve staffFullname from session
    String staffFullname = (String) session.getAttribute("staffFullname");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CC | Employee Salary Details</title>
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
            background-color: #2c2f48;
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
            background-color: #d3d3d3;
            margin-bottom: 10px;
        }

        .profile-name {
            font-size: 18px;
            margin: 0;
            font-weight: bold;
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

        .nav-links a:hover, .nav-links a.active {
            background-color: #4a4f77;
        }

        .logout {
            margin-top: auto;
            padding: 10px 15px;
            background-color: #4a4f77;
            color: white;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            width: 100%;
            text-decoration: none;
        }

        .logout:hover {
            background-color: #6a7199;
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
            color: #2c2f48;
        }

        .header .name {
            color: #4a4f77;
        }

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
            color: #2c2f48;
            margin: 0;
        }
        
        .section h3 {
            font-size: 16px;
            color: #2c2f48;
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
            background-color: #4a4f77;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .section button:hover {
            background-color: #6a7199;
        }
        .tabs a {
            text-decoration: none;
            color: #4a4f77;
            padding: 5px 10px;
            font-weight: bold;
            transition: background-color 0.3s, color 0.3s;
        }

        .tabs a:hover {
            background-color: #e1e1e1;
            color: #2c2f48;
            border-radius: 5px;
        }

        .tabs a.active {
            background-color: #4a4f77;
            color: white;
            border-radius: 5px;
        }

    </style>
</head>
<body>
    <div class="container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="profile">
                <div class="profile-pic"></div>
                <p class="profile-name"><%= staffFullname %></p>
            </div>
            <ul class="nav-links">
                <li><a href="foDashboard.jsp" class="active">Home</a></li>
                <li><a href="EmployeeListServlet">Employees</a></li>
                <li><a href="foApprovals.jsp">Approvals</a></li>
                <li><a href="foReports.jsp">Reports</a></li>
            </ul>
            <a href="logout.jsp" class="logout">Logout</a>
        </div>

        <!-- Main Content -->
        <div class="content">
            <div class="header">
                <h1><span class="name">Staff ID: ${staff.staffID}</span></h1>
            </div>

            <div class="tabs">
                <a href="EmployeeDetailsServlet?staffID=${staff.staffID}&viewType=overview">Overview</a> |
                <a href="EmployeeDetailsServlet?staffID=${staff.staffID}&viewType=salary" class="active">Salary Details</a> |
                <a href="foEmpPayslip.jsp">Payslips</a>
            </div>
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <div>
                    <h3>Salary Components</h3>
                    <p>Basic Salary: </p>
                    <p>Deductions: </p>
                    <p>Overtime Rate:</p>
                    <p>Net Pay:</p>
                </div>
                <div>
                    <h3>Monthly Amount</h3>
                    <p>${salary.salaryBasic}</p>
                    <p>${salary.salaryDeduction}</p>
                    <p>${salary.salaryOvtRate}</p>
                    <p>${salary.salaryBasic - salary.salaryDeduction}</p> <!-- Replace with another calculation logic if required -->
                </div>
            </div>
        </div>
    </div>
</body>
</html>


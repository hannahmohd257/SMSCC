<%-- 
    Document   : foDashboard
    Created on : 15 Jan 2025, 5:46:38 pm
    Author     : user
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Retrieve staffName from session
    String staffName = (String) session.getAttribute("staffName");
    String staffFullname = (String) session.getAttribute("staffFullname");

    // Redirect to login if staffName is missing
    if (staffName == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Finance Officer Dashboard</title>
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
        }

        .profile {
            text-align: center;
            margin-bottom: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;  /* Center content horizontally */
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
            text-align: center;  /* Ensure the name is centered */
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


        .chart-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .chart {
            width: 80px;
            height: 80px;
            background-color: #d3d3d3;
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
            color: #2c2f48;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Sidebar -->
        <aside class="sidebar">
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
        </aside>

        <!-- Main Content -->
        <main class="content">
            <header class="header">
                <h1>Welcome, <span class="name"><%= staffName %></span>!</h1>
            </header>

            <!-- Process Pay Run Section -->
            <section class="section">
                <div>
                    <h2>Process Pay Run</h2>
                    <p>Employees Net Pay: <strong>Processing</strong></p>
                    <p>Payment Date: <strong>30/1/2025</strong></p>
                    <p>No. of Employees: <strong>12</strong></p>
                </div>
                <button>Create Pay Run</button>
            </section>

            <!-- Payroll Summary Section -->
            <section class="section">
                <h3>Payroll Summary</h3>
                <div>
                    <div class="pie-chart-gross"></div>
                    <div class="legend-label">Gross Pay: RM____</div>
                </div>
                <div>
                    <div class="pie-chart-deductions"></div>
                    <div class="legend-label">Deductions: RM____</div>
                </div>
                <div class="section-chart-container">
                    <h2>Active Employees</h2>
                    <p class="employee-count">12</p>
                    <button><a href="EmployeeListServlet">View Employees</a></button>
                </div>
            </section>
        </main>
    </div>
</body>
</html>

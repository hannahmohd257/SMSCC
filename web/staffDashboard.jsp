<%-- 
    Document   : staffDashboard
    Created on : 15 Jan 2025, 2:13:08 am
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
    <title>CC | Staff Dashboard</title>
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

        .section p {
            margin: 5px 0;
            font-size: 16px;
            color: #333;
        }

        .section button {
            padding: 10px 20px;
            font-size: 14px;
            color: white;
            background-color: #4a4f77;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .section button:hover {
            background-color: #6a7199;
        }

        .container-payroll {
            display: flex;
            align-items: center;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .pie-chart {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            background: conic-gradient(
                #4CAF50 0% 50%, /* Netpay */
                #F44336 50% 75%, /* Deductions */
                #2196F3 75% 100% /* Gross Pay */
            );
            position: relative;
        }

        .pie-chart:before {
            content: '';
            position: absolute;
            top: 25%;
            left: 25%;
            width: 50%;
            height: 50%;
            background: #fff;
            border-radius: 50%;
        }

        .legend {
            margin-left: 20px;
        }

        .legend h2 {
            margin: 0 0 10px 0;
            font-size: 18px;
            color: #333;
        }

        .legend-item {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .legend-color {
            width: 20px;
            height: 20px;
            border-radius: 4px;
            margin-right: 10px;
        }

        .gross-pay {
            background: #2196F3;
        }

        .deductions {
            background: #F44336;
        }

        .netpay {
            background: #4CAF50;
        }

        .legend-label {
            font-size: 16px;
            color: #555;
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
                <li><a href="staffDashboard.jsp" class="active">Home</a></li>
                <li><a href="staffSalaryDetails.jsp">Salary Details</a></li>
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
                    <h2>Clock In Page</h2>
                    <p>Today's Date: <strong>30/1/2025</strong></p>
                    <p>Time: <strong>09:12:23</strong></p>
                </div>
                <button>Clock In</button>
            </section>

            <!-- Payroll Summary Section -->
            <div class="container-payroll">
                <div class="pie-chart"></div>
                <div class="legend">
                    <h2>Your Salary</h2>
                    <div class="legend-item">
                        <div class="legend-color gross-pay"></div>
                        <div class="legend-label">Gross Pay: RM____</div>
                    </div>
                    <div class="legend-item">
                        <div class="legend-color deductions"></div>
                        <div class="legend-label">Deductions: RM____</div>
                    </div>
                    <div class="legend-item">
                        <div class="legend-color netpay"></div>
                        <div class="legend-label">Netpay: RM____</div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>


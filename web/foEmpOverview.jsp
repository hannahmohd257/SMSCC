<%-- 
    Document   : foEmpOverview
    Created on : 16 Jan 2025, 3:48:01 am
    Author     : user
--%>

<%@page import="model.Staff"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Retrieve staffFullname from session
    String staffFullname = (String) session.getAttribute("staffFullname");
    Staff staff = (Staff) request.getAttribute("staff");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <title>CC | Employee Overview</title>
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

        /* Employee Details Section */
        .employee-details {
            display: flex;
            gap: 20px;
            margin-top: 20px;
        }

        .employee-card {
            background-color: #e1e1e1;
            border-radius: 10px;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 250px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .employee-card img {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            background-color: #d3d3d3;
            margin-bottom: 10px;
        }

        .employee-info {
            margin: 0;
            font-size: 14px;
            color: #333;
            text-align: center;
        }

        .personal-details {
            background-color: #e1e1e1;
            border-radius: 10px;
            padding: 20px;
            flex-grow: 1;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .personal-details h3 {
            margin-top: 0;
            font-size: 18px;
            color: #2c2f48;
        }

        .personal-details p {
            margin: 5px 0;
            font-size: 14px;
            color: #333;
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
        
        .edit-btn {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            display: flex;
            align-items: center;
        }

        .edit-btn i {
            margin-right: 5px;
        }

        .edit-btn:hover {
            background-color: #0056b3;
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
                <li><a href="foDashboard.jsp">Home</a></li>
                <li><a href="EmployeeListServlet" class="active">Employees</a></li>
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
                <a href="EmployeeDetailsServlet?staffID=${staff.staffID}&viewType=overview" class="active">Overview</a> |
                <a href="EmployeeDetailsServlet?staffID=${staff.staffID}&viewType=salary">Salary Details</a> |
                <a href="EmployeeDetailsServlet?staffID=${staff.staffID}&viewType=payslip">Payslips</a>
            </div>

            <div class="employee-details">
                <div class="employee-card">
                    <img src="#" alt="Profile Picture">
                    <p class="employee-info">Name: ${staff.staffFullname}</p>
                    <p class="employee-info">Position: ${staff.staffPosition}</p>
                    <!-- Edit button -->
                    <button class="edit-btn" onclick="window.location.href='UpdateStaffServlet?staffID=${staff.staffID}'">
                        <i class="fas fa-edit"></i> Edit
                    </button>
                </div>
                <div class="personal-details">
                    <h3>Personal Details</h3>
                    <p>Full Name: ${staff.staffFullname}</p>
                    <p>Password: ${staff.staffPassword}</p>
                    <p>Role: ${staff.staffRole}</p>
                    <p>Gender: ${staff.staffGender}</p>
                    <p>Phone Number: ${staff.staffPhoneno}</p>
                    <p>Date of Birth: ${staff.staffDOB}</p>
                    <p>Email: ${staff.staffEmail}</p>
                    <p>Residential Area: ${staff.staffAddress}</p>
                    <p>Joined Date: ${staff.staffJoinedDate}</p>
                    <p>Marital Status: ${staff.staffMaritalStatus}</p>
                    <p>Employment Type: ${staff.staffEmpType}</p>
                    <p>Bank Type: ${staff.staffBank}</p>
                    <p>Bank Account Number: ${staff.staffAccNo}</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

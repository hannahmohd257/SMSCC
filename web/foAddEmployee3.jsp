<%-- 
    Document   : foAddEmployee3
    Created on : 17 Jan 2025, 3:10:41 am
    Author     : user
--%>

<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String staffFullname = (String) session.getAttribute("staffFullname");
    String salaryBasic = (String) session.getAttribute("salaryBasic");
    String salaryDeduction = (String) session.getAttribute("salaryDeduction");
    String salaryOvtRate = (String) session.getAttribute("salaryOvtRate");
    
    if (salaryBasic != null) session.setAttribute("salaryBasic", salaryBasic);
    if (salaryDeduction != null) session.setAttribute("salaryDeduction", salaryDeduction);
    if (salaryOvtRate != null) session.setAttribute("salaryOvtRate", salaryOvtRate);
    
    Integer staffRole = (Integer) session.getAttribute("staffRole");
    Date staffDOB = (Date) session.getAttribute("staffDOB");
    String staffAddress = (String) session.getAttribute("staffAddress");
    String staffPhoneNo = request.getParameter("staffPhoneNo");
    String staffEmail = request.getParameter("staffEmail");
    String staffMaritalStatus = request.getParameter("staffMaritalStatus");
    String staffEmpType = request.getParameter("staffEmpType");

    if (staffRole == null) staffRole = 0;
    if (staffDOB == null) staffDOB = null;
    if (staffAddress == null) staffAddress = "";
    if (staffPhoneNo == null) staffPhoneNo = "";
    if (staffEmail == null) staffEmail = "";
    if (staffMaritalStatus == null) staffMaritalStatus = "";
    if (staffEmpType == null) staffEmpType = "";
%>
<!DOCTYPE html>
<html>
<head>
    <title>CC | Add New Employee</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            display: flex; /* Flex layout for the main content */
            min-height: 100vh; /* Ensure full height */
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
            flex-shrink: 0; /* Prevent shrinking of the sidebar */
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
        .container-main {
            flex: 1; /* Allow main content to expand */
            display: flex;
            flex-direction: column; /* Stack children vertically */
            align-items: center; /* Center elements horizontally */
            padding: 20px;
            gap: 20px; /* Add space between children */
        }
        h1 {
            font-size: 24px;
            color: #2c2f48;
            margin-bottom: 20px;
        }
        
        h2 {
            font-size: 24px;
            color: #2c2f48;
            margin: 0;
            text-align: center;
        }
        .steps {
            display: flex;
            justify-content: center; /* Center navigation steps horizontally */
            gap: 40px; /* Space between steps */
            margin: 0;
            width: 100%;
        }

        .step {
            text-align: center;
        }

        .circle {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: #ccc;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #fff;
            font-size: 18px;
            margin-right: 35px;
            margin-left: 35px;
            text-decoration: none;
        }

        .circle.active {
            background-color: #2c2f48;
        }

        .step-title {
            margin-top: 10px;
            font-size: 14px;
        }

        a {
            text-decoration: none;
        }

        .active {
            background-color: #2c2f48;
            color: white;
        }

        form {
            width: 100%; /* Take full width within container */
            max-width: 500px; /* Limit form width */
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 8px;
            color: #333;
        }
        input, select {
            width: 97%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .button-container {
            display: flex;
            justify-content: space-between;
        }
        button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            color: white;
            cursor: pointer;
            background-color: #2c2f48;
        }

        .cancel-btn {
            background-color: #aaa;
        }
        button:hover {
            opacity: 0.8;
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

        <!-- Navigation Steps -->
        <div class="container-main">
            <h2>Step 3: Personal Info</h2>
            <div class="steps">
                <div class="step">
                    <a href="foAddEmployee1.jsp">
                        <div class="circle">1</div>
                    </a>
                    <div class="step-title">Basics</div>
                </div>
                <div class="step">
                    <a href="foAddEmployee2.jsp">
                        <div class="circle">2</div>
                    </a>
                    <div class="step-title">Salary Details</div>
                </div>
                <div class="step">
                    <a href="foAddEmployee3.jsp">
                        <div class="circle active">3</div>
                    </a>
                    <div class="step-title">Personal Info</div>
                </div>
                <div class="step">
                    <a href="foAddEmployee4.jsp">
                        <div class="circle">4</div>
                    </a>
                    <div class="step-title">Payment Info</div>
                </div>
            </div>

            <form action="AddEmployeeServlet" method="post">
                <input type="hidden" name="step" value="3">
                <label>Role:</label>
                <input type="text" id="staffRole" name="staffRole" value="<%= staffRole %>" required><br>
                <label>Date of Birth:</label>
                <input type="date" id="staffDOB" name="staffDOB" value="<%= staffDOB %>" required><br>

                <label>Address:</label>
                <textarea name="address" id="staffAddress" value="<%= staffAddress %>" style="width: 300px; height: 50px;" required></textarea><br>

                <label>Contact Number:</label>
                <input type="text" id="staffPhoneNo" name="staffPhoneNo" value="<%= staffPhoneNo %>" required><br>

                <label>Email:</label>
                <input type="email" id="staffEmail" name="staffEmail" value="<%= staffEmail %>" required><br>

                <label for="staffMaritalStatus">Marital Status:</label>
                <select id="staffMaritalStatus" name="staffMaritalStatus">
                    <option value="Single" <%= "Single".equals(staffMaritalStatus) ? "selected" : "" %>>Single</option>
                    <option value="Married" <%= "Married".equals(staffMaritalStatus) ? "selected" : "" %>>Married</option>
                    <option value="Divorced" <%= "Divorced".equals(staffMaritalStatus) ? "selected" : "" %>>Divorced</option>
                </select><br>

                <label for="staffEmpType">Employment Type:</label>
                <select id="staffEmpType" name="staffEmpType">
                    <option value="Full-Time" <%= "Full-Time".equals(staffEmpType) ? "selected" : "" %>>Full-Time</option>
                    <option value="Part-Time" <%= "Part-Time".equals(staffEmpType) ? "selected" : "" %>>Part-Time</option>
                    <option value="Contract" <%= "Contract".equals(staffEmpType) ? "selected" : "" %>>Contract</option>
                </select><br>

                <div class="button-container">
                    <!--<button type="button" class="cancel-btn" onclick="window.location.href='CancelPage.jsp'">Cancel</button> -->
                    <!-- <button type="reset" class="cancel-btn">Cancel</button> -->
                    <button type="button" class="back-btn" onclick="window.history.back()">Back</button>
                    <button type="submit">Next</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>


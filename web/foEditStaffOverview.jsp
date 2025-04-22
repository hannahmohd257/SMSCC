<%-- 
    Document   : foEditEmpOverview
    Created on : 20 Jan 2025, 6:42:12 pm
    Author     : user
--%>

<%@page import="dao.StaffDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Staff" %>
<%
    // Retrieve staffID from request parameter
    String staffIDStr = request.getParameter("staffID");
    int staffID = Integer.parseInt(staffIDStr);
    Staff staff = (Staff) request.getAttribute("staff");
    StaffDAO staffDAO = new StaffDAO();
    String staffFullname = (String) session.getAttribute("staffFullname");
//    boolean isUpdated = staffDAO.updateStaff(staff);
//    if (isUpdated) {
//        out.println("<script>alert('Staff updated successfully!');</script>");
//    } else {
//        out.println("<script>alert('Error updating staff.');</script>");
//    }
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
        /* Fixed Sidebar */
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 250px;
            height: 100%;
            background-color: #2c2f48;
            color: white;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
            z-index: 100; /* Make sure sidebar stays on top of other content */
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
            margin-left: 260px; /* Adjust to accommodate the fixed sidebar */
            padding: 30px;
            flex-grow: 1;
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

        /* Form Styles */
        form {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        form label {
            display: block;
            margin: 10px 0 5px;
            font-size: 16px;
        }

        form input, form select, form textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        form button {
            background-color: #007bff;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        form button:hover {
            background-color: #0056b3;
        }

        /* Button Style for Cancel Link */
        .btn-secondary {
            background-color: #ccc;
            color: #333;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            width: auto; /* Allows it to shrink to fit text */
            display: inline-block;
        }

        .btn-secondary:hover {
            background-color: #999;
        }
    </style>
    <script>
        // Function to handle the form submission and show a popup
        function handleFormSubmit(event) {
            event.preventDefault();  // Prevent default form submission

            var confirmation = confirm("Are you sure you want to update the staff details?");
            if (confirmation) {
                document.getElementById("updateForm").submit(); // Submit the form if confirmed
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="profile">
                <div class="profile-pic"></div>
                <p class="profile-name"><%= staffFullname%></p>
            </div>
            <ul class="nav-links">
                <li><a href="foDashboard.jsp">Home</a></li>
                <li><a href="StaffListServlet" class="active">Employees</a></li>
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
                <a href="StaffDetailsServlet?staffID=${staff.staffID}&viewType=overview" class="active">Overview</a> |
                <a href="StaffDetailsServlet?staffID=${staff.staffID}&viewType=salary">Salary Details</a> |
                <a href="StaffDetailsServlet?staffID=${staff.staffID}&viewType=payslip">Payslips</a>
            </div>    

            <h2>Update Staff Details</h2>
            <form action="UpdateStaffServlet" method="post">
                <input type="hidden" name="staffID" value="${staff.staffID}">

                <label for="staffFullname">Full Name:</label>
                <input type="text" id="staffFullname" name="staffFullname" value="${staff.staffFullname}" required>

                <label for="staffName">Username:</label>
                <input type="text" id="staffName" name="staffName" value="${staff.staffName}" required>

                <label for="staffJoinedDate">Joined Date:</label>
                <input type="date" id="staffJoinedDate" name="staffJoinedDate" value="${staff.staffJoinedDate}" required>

                <label for="staffGender">Gender:</label>
                <select id="staffGender" name="staffGender" required>
                    <option value="Male" ${staff.staffGender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${staff.staffGender == 'Female' ? 'selected' : ''}>Female</option>
                </select>

                <label for="staffPosition">Position:</label>
                <input type="text" id="staffPosition" name="staffPosition" value="${staff.staffPosition}" required>

                <label for="staffAddress">Address:</label>
                <textarea id="staffAddress" name="staffAddress" required>${staff.staffAddress}</textarea>

                <label for="staffPhoneno">Phone Number:</label>
                <input type="text" id="staffPhoneno" name="staffPhoneno" value="${staff.staffPhoneno}" required>

                <label for="staffEmail">Email:</label>
                <input type="email" id="staffEmail" name="staffEmail" value="${staff.staffEmail}" required>

                <label for="staffMaritalStatus">Marital Status:</label>
                <select id="staffMaritalStatus" name="staffMaritalStatus" required>
                    <option value="Single" ${staff.staffMaritalStatus == 'Single' ? 'selected' : ''}>Single</option>
                    <option value="Married" ${staff.staffMaritalStatus == 'Married' ? 'selected' : ''}>Married</option>
                    <option value="Divorced" ${staff.staffMaritalStatus == 'Divorced' ? 'selected' : ''}>Divorced</option>
                </select>

                <label for="staffEmpType">Employment Type:</label>
                <select id="staffEmpType" name="staffEmpType" required>
                    <option value="Full-Time" ${staff.staffEmpType == 'Full-Time' ? 'selected' : ''}>Full-Time</option>
                    <option value="Part-Time" ${staff.staffEmpType == 'Part-Time' ? 'selected' : ''}>Part-Time</option>
                    <option value="Contract" ${staff.staffEmpType == 'Contract' ? 'selected' : ''}>Contract</option>
                </select>

                <label for="staffBank">Bank:</label>
                <input type="text" id="staffBank" name="staffBank" value="${staff.staffBank}" required>

                <label for="staffAccNo">Account Number:</label>
                <input type="text" id="staffAccNo" name="staffAccNo" value="${staff.staffAccNo}" required>

                <label for="staffDOB">Date of Birth:</label>
                <input type="date" id="staffDOB" name="staffDOB" value="${staff.staffDOB}" required>

                <label for="staffPassword">Password:</label>
                <input type="password" id="staffPassword" name="staffPassword" value="${staff.staffPassword}" required>

                <label for="staffRole">Role:</label>
                <select id="staffRole" name="staffRole" required>
                    <option value="General Staff" ${staff.staffRole == 'General Staff' ? 'selected' : ''}>General Staff</option>
                    <option value="Finance Officer" ${staff.staffRole == 'Finance Officer' ? 'selected' : ''}>Finance Officer</option>
                    <option value="Manager" ${staff.staffRole == 'Manager' ? 'selected' : ''}>Manager</option>
                </select>

                <button type="submit">Update</button>
                <button type="button" class="btn-secondary" onclick="window.location.href='StaffDetailsServlet?staffID=${staff.staffID}&viewType=overview'">Cancel</button>
            </form>
        </div>
    </div>
</body>
</html>

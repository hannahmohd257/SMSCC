<%-- 
    Document   : foEditEmpOverview
    Created on : 20 Jan 2025, 6:42:12 pm
    Author     : user
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User, model.Staff" %>
<%
    User user = (User) request.getAttribute("user");
    Staff staff = (Staff) request.getAttribute("staff"); // could be null if not staff
    String fullname = request.getParameter("fullname");
    
    String selectedBank = null;
    if (staff != null) {
        selectedBank = staff.getStaffBank();
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <title>CC | Staff Overview</title>
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
</head>
<body>
    <div class="container">
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="profile">
                <div class="profile-pic"></div>
                <p class="profile-name"><%= fullname %></p>
            </div>
            <ul class="nav-links">
                <li><a href="hrDashboard.jsp" class="active">Home</a></li>

                <li class="dropdown">
                    <a href="#">Users ▾</a> <!-- Main Dropdown Link -->
                    <ul class="dropdown-content">
                        <li><a href="UserListServlet?role=Staff">Staff</a></li>
                        <li><a href="UserListServlet?role=Finance Officer">Finance Officer</a></li>
                        <li><a href="UserListServlet?role=Manager">Manager</a></li>
                    </ul>
                </li>

                <li><a href="hrApprovals.jsp">Approvals</a></li>
                <li><a href="hrReports.jsp">Reports</a></li>
            </ul>

            <a href="logout.jsp" class="logout">Logout</a>
        </aside>
            
        <!-- Main Content --> 
        <div class="content">
            <div class="header">
                <h1><span class="name">User ID: ${user.userID}</span></h1>
            </div>

            <div class="tabs">
                <a href="UserDetailsServlet?userID=${user.userID}&viewType=overview" class="active">Overview</a> |
                <a href="UserDetailsServlet?userID=${user.userID}&viewType=salary">Salary Details</a> |
                <a href="UserDetailsServlet?userID=${user.userID}&viewType=payslip">Payslips</a>
            </div>    

            <!-- update user -->
            <form action="UpdateUserServlet" method="post">
            <input type="hidden" name="userID" value="<%= user.getUserID() %>">
            <input type="hidden" name="role" value="<%= user.getRole() %>">
            <input type="hidden" name="action" value="update">

            <div class="form-section">
                <label>Full Name:</label>
                <input type="text" name="fullname" value="<%= user.getFullname() %>" required>

                <label>Username:</label>
                <input type="text" name="username" value="<%= user.getUsername() %>" required>

                <label>Email:</label>
                <input type="email" name="email" value="<%= user.getEmail() %>" required>
            </div>

            <% if ("Staff".equalsIgnoreCase(user.getRole()) && staff != null) { %>
                <h3>Staff Details</h3>
                <div class="form-section">
                    <label>Position:</label>
                    <input type="text" name="staffPosition" value="<%= staff.getStaffPosition() %>" required>

                    <label>Address:</label>
                    <input type="text" name="staffAddress" value="<%= staff.getStaffAddress() %>" required>

                    <label>Phone Number:</label>
                    <input type="text" name="staffPhoneno" value="<%= staff.getStaffPhoneno() %>" required>

                    <label>Marital Status:</label>
                    <select name="staffMaritalStatus">
                        <option value="Single" <%= "Single".equals(staff.getStaffMaritalStatus()) ? "selected" : "" %>>Single</option>
                        <option value="Married" <%= "Married".equals(staff.getStaffMaritalStatus()) ? "selected" : "" %>>Married</option>
                    </select>

                    <label>Employment Type:</label>
                    <select name="staffEmpType">
                        <option value="Full-time" <%= "Full-time".equals(staff.getStaffEmpType()) ? "selected" : "" %>>Full-time</option>
                        <option value="Part-time" <%= "Part-time".equals(staff.getStaffEmpType()) ? "selected" : "" %>>Part-time</option>
                        <option value="Contract" <%= "Contract".equals(staff.getStaffEmpType()) ? "selected" : "" %>>Contract</option>
                    </select>

                    <label for="staffBank">Bank:</label>
                    <select id="staffBank" name="staffBank" required onchange="toggleOtherBank(this.value)">
                        <option value="" disabled <%= (selectedBank == null || selectedBank.isEmpty()) ? "selected" : "" %>>Select your bank</option>
                        <option value="Maybank" <%= "Maybank".equals(selectedBank) ? "selected" : "" %>>Maybank</option>
                        <option value="CIMB Bank" <%= "CIMB Bank".equals(selectedBank) ? "selected" : "" %>>CIMB Bank</option>
                        <option value="Public Bank" <%= "Public Bank".equals(selectedBank) ? "selected" : "" %>>Public Bank</option>
                        <option value="RHB Bank" <%= "RHB Bank".equals(selectedBank) ? "selected" : "" %>>RHB Bank</option>
                        <option value="Hong Leong Bank" <%= "Hong Leong Bank".equals(selectedBank) ? "selected" : "" %>>Hong Leong Bank</option>
                        <option value="AmBank" <%= "AmBank".equals(selectedBank) ? "selected" : "" %>>AmBank</option>
                        <option value="Bank Islam" <%= "Bank Islam".equals(selectedBank) ? "selected" : "" %>>Bank Islam</option>
                        <option value="Bank Rakyat" <%= "Bank Rakyat".equals(selectedBank) ? "selected" : "" %>>Bank Rakyat</option>
                        <option value="OCBC Bank" <%= "OCBC Bank".equals(selectedBank) ? "selected" : "" %>>OCBC Bank</option>
                        <option value="HSBC Bank" <%= "HSBC Bank".equals(selectedBank) ? "selected" : "" %>>HSBC Bank</option>
                        <option value="Standard Chartered" <%= "Standard Chartered".equals(selectedBank) ? "selected" : "" %>>Standard Chartered</option>
                        <option value="Other" <%= "Other".equals(selectedBank) ? "selected" : "" %>>Other</option>
                    </select>
                    
                    <input type="text" id="otherBankInput" name="staffBankOther" placeholder="Enter your bank" 
                        style="display: <%= "Other".equals(selectedBank) ? "block" : "none" %>;" 
                        value="<%= "Other".equals(selectedBank) ? selectedBank : "" %>">

                    <label>Account Number:</label>
                    <input type="text" name="staffAccNo" value="<%= staff.getStaffAccNo() %>" required>

                    <label>Gender:</label>
                    <select name="staffGender">
                        <option value="Male" <%= "Male".equals(staff.getStaffGender()) ? "selected" : "" %>>Male</option>
                        <option value="Female" <%= "Female".equals(staff.getStaffGender()) ? "selected" : "" %>>Female</option>
                    </select>

                    <label>Date of Birth:</label>
                    <input type="date" name="staffDOB" value="<%= staff.getStaffDOB() %>" required>

                    <label>Joined Date:</label>
                    <input type="date" name="staffJoinedDate" value="<%= staff.getStaffJoinedDate() %>" required>

                    <label>Basic Salary (RM):</label>
                    <input type="number" step="0.01" name="basicSalary" value="<%= staff.getBasicSalary() %>" required>
                </div>
                
                <script>
                    function toggleOtherBank(value) {
                        var otherInput = document.getElementById("otherBankInput");
                        if (value === "Other") {
                            otherInput.style.display = "block";
                        } else {
                            otherInput.style.display = "none";
                        }
                    }
                </script>
            <% } %>

            <div class="actions">
                <input type="submit" value="Update User">
            </div>
        </form>

        <!-- delete user -->
        <form action="UpdateUserServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this user?');">
            <input type="hidden" name="userID" value="<%= user.getUserID() %>">
            <input type="hidden" name="role" value="<%= user.getRole() %>">
            <input type="hidden" name="action" value="delete">
            <div class="actions">
                <input type="submit" value="Delete User" style="background-color: red; color: white;">
            </div>
        </form>

        <div class="actions">
            <a href="UserListServlet?role=<%= user.getRole() %>">Back to User List</a>
        </div>
</body>
</html>

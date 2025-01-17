<%-- 
    Document   : foAddEmployee
    Created on : 17 Jan 2025, 3:07:49 am
    Author     : user
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Retrieve staffFullname from session
    String staffFullname = (String) session.getAttribute("staffFullname");

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
            flex: 1; /* Allow the main content to take up the remaining space */
            justify-content: center; /* Center horizontally */
            padding: 20px; /* Add some padding */
        }
        h1 {
            font-size: 24px;
            color: #2c2f48;
            margin-bottom: 20px;
        }
        .steps {
            display: flex;
            justify-content: space-around;
            margin: 20px 0;
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
            width: 50%;
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
            margin-top: 20px;
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

        <!-- Main Content -->
        <div class="container-main">
            <h1>Add New Employee</h1>
            <!-- Navigation Steps -->
            <div class="steps">
                <div class="step">
                    <a href="foAddEmployee1.jsp">
                        <div class="circle active">1</div>
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
                        <div class="circle">3</div>
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

            <!-- Form Section -->
            <form action="AddEmployeeServlet" method="post">
                <input type="hidden" name="step" value="1">
                <label for="staffName">Staff Name</label>
                <input type="text" id="staffName" name="staffName" required>

                <label for="joinedDate">Joined Date</label>
                <input type="date" id="joinedDate" name="joinedDate" required>

                <label for="gender">Gender</label>
                <select id="gender" name="gender" required>
                    <option value="" disabled selected>Select Gender</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                </select>

                <label for="position">Position</label>
                <input type="text" id="position" name="position" required>

                <div class="button-container">
                    <button type="reset" class="cancel-btn">Cancel</button>
                    <button type="submit">Next</button>
                </div>
            </form>
        </div>
    </div>
</body>

<%-- 
    Document   : hrAddUser
    Created on : 22 Apr 2025, 11:00:30 pm
    Author     : user
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%
    // Retrieve staffName from session
    String userID = (String) session.getAttribute("userID");
    String username = (String) session.getAttribute("username");
    String fullname = (String) session.getAttribute("fullname");

    // Redirect to login if username is missing
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add User</title>
    <style>
        /* Inserted full style from your request here */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            display: flex;
            min-height: 100vh;
        }
        .container {
            display: flex;
            width: 100%;
        }
        .sidebar {
            width: 250px;
            background-color: #2c2f48;
            color: white;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
            flex-shrink: 0;
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
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
            gap: 20px;
        }
        h1, h2 {
            font-size: 24px;
            color: #2c2f48;
            margin-bottom: 20px;
            text-align: center;
        }
        .steps {
            display: flex;
            justify-content: center;
            gap: 40px;
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
            margin: 0 35px;
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
            width: 100%;
            max-width: 500px;
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
        <div class="container-main">
            <h2>Add New ${param.role}</h2>

            <form action="AddUserByRoleServlet" method="post">
                <input type="hidden" name="role" value="${param.role}" />

                <label for="fullname">Full Name:</label>
                <input type="text" name="fullname" id="fullname" required />

                <label for="username">Username:</label>
                <input type="text" name="username" id="username" required />

                <label for="password">Password:</label>
                <input type="password" name="password" id="password" required />

                <label for="email">Email:</label>
                <input type="email" name="email" id="email" required />

                <label for="position">Position:</label>
                <input type="text" name="position" id="position" required />

                <div class="button-container">
                    <button type="submit">Add ${param.role}</button>
                    <a href="UserListServlet?role=${param.role}">
                        <button type="button" class="cancel-btn">Cancel</button>
                    </a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>


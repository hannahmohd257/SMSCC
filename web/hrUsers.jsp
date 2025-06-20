<%-- 
    Document   : foEmployees
    Created on : 16 Jan 2025, 2:00:52 am
    Author     : user
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Retrieve staffFullname from session
    String fullname = (String) session.getAttribute("fullname");

%>
<!DOCTYPE html>
<html>
<head>
    <title>CC | User List</title>
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

        .container-list {
            display: flex;
            justify-content: center;  /* Center horizontally */
            align-items: flex-start;  /* Align items to the top */
            width: 100%;
            margin-left: 250px;  /* Offset the sidebar */
            padding: 20px;
        }

        table {
            width: 100%;
            max-width: 1055px;  /* Optional: to avoid the table stretching too much */
            border-collapse: collapse;
            margin: 0 auto;  /* This ensures the table is centered */
        }


        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #2c2f48;
            color: white;
        }
        .add-button {
            float: right;
            text-decoration: none;
            background-color: #4a4f77;
            color: white;
            padding: 10px 20px;
            font-size: 14px;
            border-radius: 5px;
            margin-top: -10px;
            transition: background-color 0.3s;
        }

        .add-button:hover {
            background-color: #6a7199;
        }
        
        .icon-button {
            color: #4a4f77;
            font-size: 18px;
            margin-right: 10px;
            text-decoration: none;
            display: inline-block;
        }

        .icon-button:hover {
            color: #2c2f48;
        }
        .icon-button trash {
            text-decoration: none;
            color: #333;
            margin: 0 5px;
        }
        .icon-button trash:hover {
            color: #d9534f; /* Red color on hover */
        }
        .icon-button i {
            font-size: 16px;
        }
    </style>
</head>
<body>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <div class="container">
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="profile">
                <div class="profile-pic"></div>
                <p class="profile-name"><%= fullname %></p>
            </div>
            <ul class="nav-links">
                <li><a href="hrDashboard.jsp">Home</a></li>

                <li class="dropdown">
                    <a href="#" class="active">Users ▾</a> <!-- Main Dropdown Link -->
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

        <main class="content">
            <div class="header">
                <!-- Dynamically set the title based on the role -->
                <h2 style="display: inline-block;">
                    <c:choose>
                        <c:when test="${param.role == 'Staff'}">Staff</c:when>
                        <c:when test="${param.role == 'Finance Officer'}">Finance Officer</c:when>
                        <c:when test="${param.role == 'Manager'}">Manager</c:when>
                        <c:otherwise>Users</c:otherwise>
                    </c:choose>
                </h2>

                <c:choose>
                    <c:when test="${param.role == 'Staff'}">
                        <a href="hrAddStaff1.jsp" class="add-button">Add New ${param.role}</a>
                    </c:when>
                    <c:otherwise>
                        <c:url var="addUserUrl" value="hrAddUser.jsp">
                            <c:param name="role" value="${param.role}" />
                        </c:url>
                        <a href="${addUserUrl}" class="add-button">Add New ${param.role}</a>
                    </c:otherwise>
                </c:choose>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Actions</th> <!-- Actions column for Edit/Delete -->
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterate over userList (generic for Staff, Finance Officer, Manager) -->
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td>${user.userID}</td>
                            <td><a href="UserDetailsServlet?userID=${user.userID}&viewType=overview">${user.fullname}</a></td>
                            <td>${user.email}</td>
                            <td>
                                <!-- Edit Button with Icon -->
                                <a href="UpdateUserServlet?userID=${user.userID}&editMode=true" class="icon-button">
                                    <i class="fa fa-edit"></i>
                                </a>

                                <!-- Delete Button with Icon -->
                                <a href="UpdateUserServlet?action=delete&userID=${user.userID}" 
                                    class="icon-button trash" 
                                    onclick="return confirmDelete('${user.fullname}')">
                                    <i class="fa fa-trash"></i>
                                 </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </main>
    </div>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script>
        function confirmDelete(userName) {
            var result = confirm("Are you sure you want to delete " + userName + "? This action cannot be undone.");
            return result; // If confirmed, will proceed with the delete, otherwise will cancel the action.
        }
    </script>
</body>
</html>

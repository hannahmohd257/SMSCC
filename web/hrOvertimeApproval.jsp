<%-- 
    Document   : hrOvertimeApproval
    Created on : 3 Jun 2025, 11:38:45?pm
    Author     : user
--%>

<%@page import="model.User"%>
<%@ page import="java.util.*, dao.OvertimeDAO, model.OvertimeRequest" %>
<%
    String fullname = (String) session.getAttribute("fullname");

    
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    OvertimeDAO dao = new OvertimeDAO();
    List<OvertimeRequest> requests = dao.getPendingRequests();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CC | Overtime Approval</title>
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
/*            display: flex;
            justify-content: space-between;
            align-items: center;*/
            display: block;
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
            table.dashboard-table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0 8px; /* Adds vertical space between rows */
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                font-size: 14px;
                color: #444;
            }

            table.dashboard-table thead tr {
                background-color: #2f80ed;
                color: white;
                text-align: left;
            }

            table.dashboard-table th, table.dashboard-table td {
                padding: 12px 15px;
            }

            table.dashboard-table tbody tr {
                background-color: #fff;
                box-shadow: 0 2px 5px rgb(0 0 0 / 0.1);
                border-radius: 6px;
                transition: background-color 0.3s ease;
            }

            table.dashboard-table tbody tr:hover {
                background-color: #f0f4ff;
            }

            table.dashboard-table tbody td form {
                display: inline-block;
                margin-right: 5px;
            }

            table.dashboard-table tbody td form input[type="submit"] {
                background-color: #2f80ed;
                border: none;
                color: white;
                padding: 6px 12px;
                font-weight: 600;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            table.dashboard-table tbody td form input[type="submit"]:hover {
                background-color: #1366d6;
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
                    <li><a href="staffDashboard.jsp" class="active">Home</a></li>
                    <li><a href="staffSalaryDetails.jsp">Salary Details</a></li>
                </ul>
                <a href="logout.jsp" class="logout">Logout</a>
            </aside>

            <!-- Main Content -->
            <main class="content">
                <table class="dashboard-table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>User</th>
                            <th>Time</th>
                            <th>Reason</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% for (OvertimeRequest ot : requests) { %>
                        <tr>
                            <td><%= ot.getOtDate() %></td>
                            <td><%= ot.getFullname() %></td>
                            <td><%= ot.getStartTime() %> - <%= ot.getEndTime() %></td>
                            <td><%= ot.getReason() %></td>
                            <td>
                                <form action="ManageOvertimeServlet" method="post">
                                    <input type="hidden" name="overtimeID" value="<%= ot.getOvertimeID() %>">
                                    <input type="hidden" name="action" value="approve">
                                    <input type="submit" value="Approve">
                                </form>
                                <form action="ManageOvertimeServlet" method="post">
                                    <input type="hidden" name="overtimeID" value="<%= ot.getOvertimeID() %>">
                                    <input type="hidden" name="action" value="reject">
                                    <input type="submit" value="Reject">
                                </form>
                            </td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </main>
        </div>
    </body>
</html>



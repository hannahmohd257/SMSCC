<%-- 
    Document   : staffDashboard
    Created on : 15 Jan 2025, 2:13:08 am
    Author     : user
--%>

<%@page import="model.OvertimeRequest"%>
<%@page import="dao.OvertimeDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.time.DayOfWeek"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.LeaveRequest" %>
<%@ page import="dao.LeaveRequestDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Retrieve staffName from session
    String username = (String) session.getAttribute("username");
    String fullname = (String) session.getAttribute("fullname");

    
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
    List<LeaveRequest> requests = leaveDAO.getLeaveRequestsByUser(user.getUserID());
    
    OvertimeDAO dao = new OvertimeDAO();
    List<OvertimeRequest> overtimeList = dao.getRequestsByUser(user.getUserID());
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
        /* Unified Table Styling */
        table.custom-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            font-family: Arial, sans-serif;
            font-size: 14px;
            color: #333;
        }

        table.custom-table thead tr {
            background-color: #f2f2f2;
        }

        table.custom-table th, table.custom-table td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }

        table.custom-table tbody tr:hover {
            background-color: #e9e9e9;
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
            <header class="header">
                <h1>Welcome, <span class="name"><%= username %></span>!</h1>
            </header>

            <!-- Clock In Section -->
            <section class="section">
                <div>
<!--                    <h2>Clock In Page</h2>-->

                    <%
                        // Get current date, time, and day
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String currentDate = now.format(dateFormatter);
                        String currentTime = now.format(timeFormatter);
                        DayOfWeek dayOfWeek = now.getDayOfWeek();
                    %>

                    <p>Date: <strong><%= currentDate %></strong></p>
                    <p>Day: <strong><%= dayOfWeek %></strong></p>
                    <p>Time: <strong><%= currentTime %></strong></p>
                </div>

                <%
                    String userID = (String) session.getAttribute("userID");
                    boolean alreadyClockedIn = false;
                    boolean alreadyClockedOut = false;

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "password");
                        PreparedStatement ps = conn.prepareStatement("SELECT clockIn, clockOut FROM attendance WHERE userID=? AND date=CURDATE()");
                        ps.setString(1, userID);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            alreadyClockedIn = rs.getTimestamp("clockIn") != null;
                            alreadyClockedOut = rs.getTimestamp("clockOut") != null;
                        }
                        rs.close();
                        ps.close();
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                %>
                
                <%
                    String clockMessage = (String) session.getAttribute("clockMessage");
                    if (clockMessage != null) { 
                %>
                    <p style="color: green;"><%= clockMessage %></p>
                <%
                        session.removeAttribute("clockMessage");
                    }
                %>

                <% if (!alreadyClockedIn) { %>
                    <form action="ClockServlet" method="post">
                        <input type="hidden" name="action" value="clockin">
                        <button type="submit">Clock In</button>
                    </form>
                <% } else if (!alreadyClockedOut) { %>
                    <form action="ClockServlet" method="post">
                        <input type="hidden" name="action" value="clockout">
                        <button type="submit">Clock Out</button>
                    </form>
                <% } else { %>
                    <p>You have already clocked in and out for today.</p>
                <% } %>
                
                <hr style="margin: 30px 0; border: none; border-top: 1px solid #ccc;">

                <h2>Leave Application</h2>
                <button class="btn btn-primary" onclick="location.href='staffLeaveRequest.jsp'">Request Leave</button>
                
                <table class="custom-table">
                    <thead>
                        <tr>
                            <th>Request Date</th>
                            <th>Leave Type</th>
                            <th>Duration</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Reason</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (LeaveRequest req : requests) { %>
                        <tr>
                            <td><%= req.getRequestDate() %></td>
                            <td><%= req.getLeaveType() %></td>
                            <td><%= req.getDurationType() %></td>
                            <td><%= req.getStartDate() %></td>
                            <td><%= req.getEndDate() %></td>
                            <td><%= req.getFromTime() %></td>
                            <td><%= req.getToTime() %></td>
                            <td><%= req.getReason() %></td>
                            <td><%= req.getStatus() %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>


            <hr style="margin: 30px 0; border: none; border-top: 1px solid #ccc;">
            <h2>Overtime Application</h2>
                <button class="btn btn-primary" onclick="location.href='staffOvertimeRequest.jsp'">Request Overtime</button>
                
                <table class="custom-table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Reason</th>
                            <th>Status</th>
                            <th>Remarks</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        if (overtimeList.isEmpty()) {
                        %>
                            <tr>
                                <td colspan="6" style="text-align:center; padding:10px;">No overtime requests found.</td>
                            </tr>
                        <%
                        } else {
                            for (OvertimeRequest ot : overtimeList) {
                        %>
                        <tr>
                            <td><%= ot.getOtDate() %></td>
                            <td><%= ot.getStartTime() %></td>
                            <td><%= ot.getEndTime() %></td>
                            <td><%= ot.getReason() %></td>
                            <td><%= ot.getStatus() %></td>
                            <td><%= ot.getRemarks() == null ? "-" : ot.getRemarks() %></td>
                        </tr>
                        <%
                            }
                        }
                        %>
                    </tbody>
                </table>
        </main>
    </div>
</body>
</html>

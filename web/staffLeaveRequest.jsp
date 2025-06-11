<%-- 
    Document   : staffLeaveRequestForm
    Created on : 28 May 2025, 10:21:22 pm
    Author     : user
--%>

<%
    // Retrieve staffName from session
    String fullname = (String) session.getAttribute("fullname");
    String message = (String) session.getAttribute("leaveMessage");
    if (message != null) {
%>
    <script>
        alert("<%= message %>");
    </script>
<%
        session.removeAttribute("leaveMessage");
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Leave Request Form</title>
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
            padding: 40px;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            background-color: #f4f4f9;
            height: 100vh;
            overflow-y: auto;
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

        .form-container {
            max-width: 800px;
            margin: 3rem auto;
            background: #ffffff;
            padding: 2rem 2.5rem;
            border-radius: 1rem;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
        }

        .form-container h1 {
            font-size: 1.75rem;
            font-weight: 600;
            margin-bottom: 1.5rem;
            color: #333;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            font-weight: 500;
            margin-bottom: 0.5rem;
            color: #444;
        }

        .form-group input,
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 0.75rem 1rem;
            border: 1px solid #ccc;
            border-radius: 0.75rem;
            font-size: 1rem;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        .form-group input:focus,
        .form-group select:focus,
        .form-group textarea:focus {
            border-color: #4f46e5;
            box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
            outline: none;
        }

        .form-actions {
            display: flex;
            justify-content: flex-end;
            gap: 0.75rem;
        }

        .btn {
            padding: 0.6rem 1.25rem;
            font-size: 0.95rem;
            border-radius: 0.65rem;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn-submit {
            background-color: #4f46e5;
            color: white;
        }

        .btn-submit:hover {
            background-color: #4338ca;
        }

        .btn-cancel {
            background-color: #f3f4f6;
            color: #374151;
            text-decoration: none;
            display: inline-block;
        }

        .btn-cancel:hover {
            background-color: #e5e7eb;
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

        <div class="content">
            <div class="form-container">
                <h1>Request Leave</h1>
                <form action="LeaveRequestServlet" method="post">
                    <input type="hidden" name="action" value="submit" />

                    <div class="form-group">
                        <label for="durationType">Leave Duration</label>
                        <select id="durationType" name="durationType" required onchange="toggleLeaveFields()">
                            <option value="" disabled selected>Select leave duration</option>
                            <option value="full">Full Day(s) Leave</option>
                            <option value="partial">Partial Day (Hours)</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="leaveType">Leave Type</label>
                        <select name="leaveType" id="leaveType" required>
                            <option value="" disabled selected>Select type</option>
                            <option value="Annual Leave">Annual Leave</option>
                            <option value="Medical Leave">Medical Leave</option>
                            <option value="Emergency Leave">Emergency Leave</option>
                            <option value="Unpaid Leave">Unpaid Leave</option>
                        </select>
                    </div>

                    <!-- Full Day Fields -->
                    <div id="fullDayFields" style="display:none;">
                        <div class="form-group">
                            <label for="startDate">From Date</label>
                            <input type="date" id="startDate" name="startDate">
                        </div>
                        <div class="form-group">
                            <label for="endDate">To Date</label>
                            <input type="date" id="endDate" name="endDate">
                        </div>
                    </div>

                    <!-- Partial Day Fields -->
                    <div id="partialDayFields" style="display:none;">
                        <div class="form-group">
                            <label for="leaveDate">Date</label>
                            <input type="date" id="leaveDate" name="partialDate">
                        </div>
                        <div class="form-group">
                            <label for="fromTime">From Time</label>
                            <input type="time" id="fromTime" name="fromTime">
                        </div>
                        <div class="form-group">
                            <label for="toTime">To Time</label>
                            <input type="time" id="toTime" name="toTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="reason">Reason</label>
                        <textarea id="reason" name="reason" rows="4" required></textarea>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-submit">Submit</button>
                        <a href="staffDashboard.jsp" class="btn btn-cancel">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        function toggleLeaveFields() {
            const type = document.getElementById('durationType').value;
            document.getElementById('fullDayFields').style.display = (type === 'full') ? 'block' : 'none';
            document.getElementById('partialDayFields').style.display = (type === 'partial') ? 'block' : 'none';

            // Set required attributes accordingly
            document.getElementById('startDate').required = (type === 'full');
            document.getElementById('endDate').required = (type === 'full');
            document.getElementById('leaveDate').required = (type === 'partial');
            document.getElementById('fromTime').required = (type === 'partial');
            document.getElementById('toTime').required = (type === 'partial');
        }
    </script>
</body>
</html>
<%-- 
    Document   : staffClocking
    Created on : 21 Jan 2025, 7:01:33?am
    Author     : user
--%>

<%@ page import="java.util.*, dao.AttendanceDAO, model.Attendance" %>
<%@ page import="dao.AttendanceDAO" %>
<%@ page import="model.Attendance" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Clock In/Out</title>
    <script>
        // Function to toggle between clock in and clock out
        function toggleClock(action) {
            // Create a new form element
            var form = document.createElement("form");
            form.method = "GET";  // Use GET for the request

            // Create a hidden input field to send the action (clockin or clockout)
            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "action";  // The parameter name expected by your servlet
            input.value = action;   // Set the value based on the action (clockin or clockout)
            form.appendChild(input);

            // Set the action URL to the ClockServlet
            form.action = "ClockServlet";  // Adjust this to the correct path to your servlet

            // Append the form to the document and submit it
            document.body.appendChild(form);
            form.submit();  // Submit the form
        }
    </script>
 

</head>
<body>
    <h1>Welcome, ${staffFullName}</h1>

    <%
        int staffID = (int) session.getAttribute("staffID");
        AttendanceDAO dao = new AttendanceDAO();
        Attendance todayAttendance = dao.getTodayAttendance(staffID);
        boolean clockedIn = todayAttendance != null && todayAttendance.getAttendanceClockOut() == null;
    %>

    <!-- Button toggles between Clock In and Clock Out -->
    <button onclick="toggleClock('<%= clockedIn ? "clockout" : "clockin" %>')">
        <%= clockedIn ? "Clock Out" : "Clock In" %>
    </button>

    <h2>Your Attendance Records:</h2>
    <table>
        <thead>
            <tr>
                <th>Date</th>
                <th>Clock In</th>
                <th>Clock Out</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Attendance> records = dao.getAllAttendance(staffID);
                for (Attendance record : records) {
            %>
                <tr>
                    <td><%= record.getAttendanceDate() %></td>
                    <td><%= record.getAttendanceClockIn() %></td>
                    <td><%= record.getAttendanceClockOut() != null ? record.getAttendanceClockOut() : "-" %></td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>

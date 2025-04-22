/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.IOException;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.Staff;
import util.DBConnection;

public class ClockServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        // Check if staffID is available
        Integer staffID = (Integer) session.getAttribute("staffID");
        if (staffID == null) {
            response.sendRedirect("login.jsp"); // or any login page
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps;
            ResultSet rs;

            // Get current timestamp info
            LocalDateTime now = LocalDateTime.now();
            String currentDate = now.toLocalDate().toString(); // yyyy-MM-dd
            String currentTime = now.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            DayOfWeek dayOfWeek = now.getDayOfWeek();

            System.out.println("Staff ID " + staffID + " attempted " + action + " on " + currentDate + " (" + dayOfWeek + ") at " + currentTime);

            if ("clockin".equals(action)) {
                // Check if already clocked in
                ps = conn.prepareStatement("SELECT attendanceClockIn FROM attendance WHERE staffID = ? AND attendanceDate = CURDATE()");
                ps.setInt(1, staffID);
                rs = ps.executeQuery();

                if (rs.next() && rs.getTimestamp("attendanceClockIn") != null) {
                    session.setAttribute("message", "You have already clocked in today.");
                } else {
                    // Insert new attendance row
                    ps = conn.prepareStatement("INSERT INTO attendance (staffID, attendanceClockIn, attendanceDate) VALUES (?, NOW(), CURDATE())");
                    ps.setInt(1, staffID);
                    ps.executeUpdate();
                    session.setAttribute("message", "Clock In successful at " + currentTime + " (" + dayOfWeek + ")");
                }

            } else if ("clockout".equals(action)) {
                // Check if clock-in exists and not yet clocked out
                ps = conn.prepareStatement("SELECT attendanceClockOut FROM attendance WHERE staffID = ? AND attendanceDate = CURDATE()");
                ps.setInt(1, staffID);
                rs = ps.executeQuery();

                if (rs.next() && rs.getTimestamp("attendanceClockOut") == null) {
                    ps = conn.prepareStatement("UPDATE attendance SET attendanceClockOut = NOW() WHERE staffID = ? AND attendanceDate = CURDATE()");
                    ps.setInt(1, staffID);
                    ps.executeUpdate();
                    session.setAttribute("message", "Clock Out successful at " + currentTime + " (" + dayOfWeek + ")");
                } else {
                    session.setAttribute("message", "You need to clock in before clocking out.");
                }

            } else {
                session.setAttribute("message", "Invalid action.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage(), e);
        }

        // Redirect back to clockin page after processing
        response.sendRedirect("staffClockIn.jsp");
    }
}

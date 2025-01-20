/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import util.DBConnection;

public class ClockServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        int staffID = (int) session.getAttribute("staffID"); // Ensure staffId is set in the session

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps;

            if ("clockin".equals(action)) {
                // Check if already clocked in for today
                ps = conn.prepareStatement("SELECT attendanceClockIn FROM attendance WHERE staffID = ? AND attendanceDate = CURDATE()");
                ps.setInt(1, staffID);
                ResultSet rs = ps.executeQuery();

                if (rs.next() && rs.getTimestamp("attendanceClockIn") != null) {
                    response.getWriter().write("You have already clocked in today!");
                } else {
                    // Insert clock-in record
                    ps = conn.prepareStatement("INSERT INTO attendance (staffID, attendanceClockIn, attendanceDate) VALUES (?, NOW(), CURDATE())");
                    ps.setInt(1, staffID);
                    ps.executeUpdate();
                    response.getWriter().write("Clock In successful!");
                }
            } else if ("clockout".equals(action)) {
                // Check if clocked in but not yet clocked out
                ps = conn.prepareStatement("SELECT attendanceClockOut FROM attendance WHERE staffID = ? AND attendanceDate = CURDATE()");
                ps.setInt(1, staffID);
                ResultSet rs = ps.executeQuery();

                if (rs.next() && rs.getTimestamp("attendanceClockOut") == null) {
                    // Update clock-out time
                    ps = conn.prepareStatement("UPDATE attendance SET attendanceClockOut = NOW() WHERE staffID = ? AND attendanceDate = CURDATE()");
                    ps.setInt(1, staffID);
                    ps.executeUpdate();
                    response.getWriter().write("Clock Out successful!");
                } else {
                    response.getWriter().write("You need to clock in before clocking out!");
                }
            } else {
                response.getWriter().write("Invalid action!");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
}

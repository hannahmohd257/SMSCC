/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;


import java.io.IOException;
import java.sql.*;
import java.time.*;
import javax.servlet.*;
import javax.servlet.http.*;
import util.DBConnection;

public class ClockServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userID = (String) request.getSession().getAttribute("userID");
        LocalDate today = LocalDate.now();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps;

            if ("clockin".equals(action)) {
                ps = conn.prepareStatement(
                    "INSERT INTO attendance (userID, clockIn, date) VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE clockIn = IFNULL(clockIn, VALUES(clockIn))"
                );
                ps.setString(1, userID);
                ps.setTimestamp(2, now);
                ps.setDate(3, Date.valueOf(today));
                ps.executeUpdate();
                request.getSession().setAttribute("clockMessage", "You have clocked in successfully.");
            } else if ("clockout".equals(action)) {
                ps = conn.prepareStatement("UPDATE attendance SET clockOut=? WHERE userID=? AND date=? AND clockIn IS NOT NULL");
                ps.setTimestamp(1, now);
                ps.setString(2, userID);
                ps.setDate(3, Date.valueOf(today));
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    request.getSession().setAttribute("clockMessage", "You have clocked out successfully.");
                } else {
                    request.getSession().setAttribute("clockMessage", "Please clock in before clocking out.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("clockMessage", "An error occurred while processing your request.");
        }

        // Redirect instead of forward
        response.sendRedirect("staffDashboard.jsp");
    }
}


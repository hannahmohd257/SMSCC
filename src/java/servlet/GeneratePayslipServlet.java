/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import util.DBConnection;

public class GeneratePayslipServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the request
        int staffID = Integer.parseInt(request.getParameter("staffID"));
        String payslipMonth = request.getParameter("payslipMonth");

        // Initialize payslip fields
        double salaryBasic = 0.0;
        double salaryOvtRate = 0.0;
        int overtimeHour = 0;
        double deductionAmount = 0.0;
        double payslipOvertimePay = 0.0;
        double payslipNetPay = 0.0;

        try (Connection conn = DBConnection.getConnection()) {
            // Fetch salary details
            String salaryQuery = "SELECT salaryBasic, salaryOvtRate FROM salary WHERE staffID = ?";
            try (PreparedStatement salaryStmt = conn.prepareStatement(salaryQuery)) {
                salaryStmt.setInt(1, staffID);
                ResultSet rs = salaryStmt.executeQuery();
                if (rs.next()) {
                    salaryBasic = rs.getDouble("salaryBasic");
                    salaryOvtRate = rs.getDouble("salaryOvtRate");
                }
            }

            // Fetch overtime hours
            String overtimeQuery = "SELECT overtimeHour FROM overtime WHERE staffID = ? AND month = ?";
            try (PreparedStatement overtimeStmt = conn.prepareStatement(overtimeQuery)) {
                overtimeStmt.setInt(1, staffID);
                overtimeStmt.setString(2, payslipMonth);
                ResultSet rs = overtimeStmt.executeQuery();
                if (rs.next()) {
                    overtimeHour = rs.getInt("overtimeHour");
                }
            }

            // Fetch deduction amount
            String deductionQuery = "SELECT deductionAmount FROM deduction WHERE deductionID = " +
                    "(SELECT deductionID FROM staff WHERE staffID = ?)";
            try (PreparedStatement deductionStmt = conn.prepareStatement(deductionQuery)) {
                deductionStmt.setInt(1, staffID);
                ResultSet rs = deductionStmt.executeQuery();
                if (rs.next()) {
                    deductionAmount = rs.getDouble("deductionAmount");
                }
            }

            // Perform calculations
            payslipOvertimePay = salaryOvtRate * overtimeHour;
            payslipNetPay = salaryBasic + payslipOvertimePay - deductionAmount;

            // Save payslip details to database
            String insertPayslipQuery = "INSERT INTO payslip (staffID, payslipOvertimePay, payslipNetPay, payslipMonth, payslipCreatedAt) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertPayslipQuery)) {
                insertStmt.setInt(1, staffID);
                insertStmt.setDouble(2, payslipOvertimePay);
                insertStmt.setDouble(3, payslipNetPay);
                insertStmt.setString(4, payslipMonth);
                insertStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                insertStmt.executeUpdate();
            }

            // Pass the generated payslip to the JSP for display
            request.setAttribute("payslipOvertimePay", payslipOvertimePay);
            request.setAttribute("payslipNetPay", payslipNetPay);
            request.setAttribute("payslipMonth", payslipMonth);
            request.getRequestDispatcher("payslip.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}


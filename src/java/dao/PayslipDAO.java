/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import model.Payslip;
import util.DBConnection;


public class PayslipDAO {

    public Payslip getPayslip(int payslipID) {
        String query = "SELECT * FROM payslip WHERE payslipID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, payslipID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Payslip payslip = new Payslip();
                payslip.setPayslipID(rs.getInt("payslipID"));
                payslip.setStaffID(rs.getInt("staffID"));
                payslip.setPayslipSalaryAmount(rs.getDouble("payslipSalaryAmount"));
                payslip.setPayslipDeductionAmount(rs.getDouble("payslipDeductionAmount"));
                payslip.setPayslipOvertimePay(rs.getDouble("payslipOvertimePay"));
                payslip.setPayslipNetPay(rs.getDouble("payslipNetPay"));
                payslip.setPayslipMonth(rs.getString("payslipMonth"));
                payslip.setPayslipCreatedAt(rs.getTimestamp("payslipCreatedAt"));
                return payslip;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void savePayslip(Payslip payslip) {
        String query = "INSERT INTO payslips (staffID, payslipSalaryAmount, payslipDeductionAmount, payslipOvertimePay, payslipNetPay, payslipMonth, payslipCreatedAt) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, payslip.getStaffID());
            stmt.setDouble(2, payslip.getPayslipSalaryAmount());
            stmt.setDouble(3, payslip.getPayslipDeductionAmount());
            stmt.setDouble(4, payslip.getPayslipOvertimePay());
            stmt.setDouble(5, payslip.getPayslipNetPay());
            stmt.setString(6, payslip.getPayslipMonth());
            stmt.setTimestamp(7, payslip.getPayslipCreatedAt());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                payslip.setPayslipID(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Payslip calculatePayslip(int staffID, String payslipMonth) {
        Payslip payslip = new Payslip();

        String salaryQuery = "SELECT salaryBasic, salaryOvtRate FROM salary WHERE staffID = ?";
        String overtimeQuery = "SELECT overtimeHour FROM overtime WHERE staffID = ? AND month = ?";
        String deductionQuery = "SELECT deductionAmount FROM deduction WHERE deductionID = " +
                                "(SELECT deductionID FROM staff WHERE staffID = ?)";

        try (Connection conn = DBConnection.getConnection()) {
            // Fetch salary details
            try (PreparedStatement salaryStmt = conn.prepareStatement(salaryQuery)) {
                salaryStmt.setInt(1, staffID);
                ResultSet rsSalary = salaryStmt.executeQuery();
                if (rsSalary.next()) {
                    double salaryBasic = rsSalary.getDouble("salaryBasic");
                    double salaryOvtRate = rsSalary.getDouble("salaryOvtRate");
                    payslip.setPayslipSalaryAmount(salaryBasic);

                    // Fetch overtime details
                    try (PreparedStatement overtimeStmt = conn.prepareStatement(overtimeQuery)) {
                        overtimeStmt.setInt(1, staffID);
                        overtimeStmt.setString(2, payslipMonth);
                        ResultSet rsOvertime = overtimeStmt.executeQuery();
                        if (rsOvertime.next()) {
                            int overtimeHour = rsOvertime.getInt("overtimeHour");
                            double overtimePay = salaryOvtRate * overtimeHour;
                            payslip.setPayslipOvertimePay(overtimePay);
                        }
                    }
                }
            }

            // Fetch deduction amount
            try (PreparedStatement deductionStmt = conn.prepareStatement(deductionQuery)) {
                deductionStmt.setInt(1, staffID);
                ResultSet rsDeduction = deductionStmt.executeQuery();
                if (rsDeduction.next()) {
                    double deductionAmount = rsDeduction.getDouble("deductionAmount");
                    payslip.setPayslipDeductionAmount(deductionAmount);
                }
            }

            // Calculate net pay
            double netPay = payslip.getPayslipSalaryAmount() + payslip.getPayslipOvertimePay() - payslip.getPayslipDeductionAmount();
            payslip.setPayslipNetPay(netPay);

            // Set other payslip details
            payslip.setStaffID(staffID);
            payslip.setPayslipMonth(payslipMonth);
            payslip.setPayslipCreatedAt(new Timestamp(System.currentTimeMillis()));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payslip;
    }



}

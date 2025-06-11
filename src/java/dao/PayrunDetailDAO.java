/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import model.PayrunDetails;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class PayrunDetailDAO {

    public List<PayrunDetails> generateAndInsertPayrunDetails(int payrunID, String month, String year) {
        List<PayrunDetails> detailList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            // Step 1: Get all active staff/user records with basic salary
            String userSql = "SELECT userID, username, basicSalary FROM user WHERE status = 'Active'";
            PreparedStatement userStmt = conn.prepareStatement(userSql);
            ResultSet userRs = userStmt.executeQuery();

            while (userRs.next()) {
                String userID = userRs.getString("userID");
                String username = userRs.getString("username");
                double basicSalary = userRs.getDouble("basicSalary");

                // Step 2: Get total overtime hours
                double overtimeHours = getOvertimeHours(conn, userID, month, year);
                double overtimeRate = 10.0; // You can externalize this rate
                double overtimePay = overtimeHours * overtimeRate;

                // Step 3: Get total deductions (optional, for example unpaid leave)
                double deductions = getDeductions(conn, userID, month, year);

                // Step 4: Calculate net salary
                double netSalary = basicSalary + overtimePay - deductions;

                // Step 5: Insert into payrundetail
                String insertSql = "INSERT INTO payrundetail (payrunID, userID, basicSalary, overtimeHour, overtimePay, deductions, netSalary) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setInt(1, payrunID);
                insertStmt.setString(2, userID);
                insertStmt.setDouble(3, basicSalary);
                insertStmt.setDouble(4, overtimeHours);
                insertStmt.setDouble(5, overtimePay);
                insertStmt.setDouble(6, deductions);
                insertStmt.setDouble(7, netSalary);
                insertStmt.executeUpdate();

                // Step 6: Get generated detail ID
                int detailID = 0;
                ResultSet keys = insertStmt.getGeneratedKeys();
                if (keys.next()) {
                    detailID = keys.getInt(1);
                }

                // Step 7: Create object and add to list
                PayrunDetails details = new PayrunDetails();
                details.setDetailID(detailID);
                details.setPayrunID(payrunID);
                details.setUserID(userID);
                details.setUsername(username);
                details.setBasicSalary(basicSalary);
                details.setOvertimeHours(overtimeHours);
                details.setOvertimePay(overtimePay);
                details.setDeductions(deductions);
                details.setNetSalary(netSalary);

                detailList.add(details);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailList;
    }

    private double getOvertimeHours(Connection conn, String userID, String month, String year) throws SQLException {
        String sql = "SELECT SUM(hours) FROM overtime WHERE user_id = ? AND MONTH(overtime_date) = ? AND YEAR(overtime_date) = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, userID);
        stmt.setInt(2, Integer.parseInt(month));
        stmt.setInt(3, Integer.parseInt(year));
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble(1);
        }
        return 0.0;
    }

    private double getDeductions(Connection conn, String userID, String month, String year) throws SQLException {
        String sql = "SELECT SUM(amount) FROM deductions WHERE user_id = ? AND MONTH(deduction_date) = ? AND YEAR(deduction_date) = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, userID);
        stmt.setInt(2, Integer.parseInt(month));
        stmt.setInt(3, Integer.parseInt(year));
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble(1);
        }
        return 0.0;
    }
}

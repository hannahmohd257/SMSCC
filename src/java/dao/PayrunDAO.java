/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Payrun;
import model.PayrunDetails;
import util.DBConnection;

public class PayrunDAO {
    private Connection conn;

    public PayrunDAO(Connection conn) {
        this.conn = conn;
    }

    public int createPayrun(int year, int month, String createdBy) throws SQLException {
        String sql = "INSERT INTO payrun (month, year, status, totalSalary, createdBy) VALUES (?, ?, 'Pending', 0, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, month);
        stmt.setInt(2, year);
        stmt.setString(3, createdBy);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // return payrunID
        }
        return -1;
    }

    public void addPayrunDetail(int payrunID, String userID, double basic, double otHours, double otPay, double deductions, double netSalary) throws SQLException {
        String sql = "INSERT INTO payrundetail (payrunID, userID, basicSalary, overtimeHours, overtimePay, deductions, netSalary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, payrunID);
        stmt.setString(2, userID);
        stmt.setDouble(3, basic);
        stmt.setDouble(4, otHours);
        stmt.setDouble(5, otPay);
        stmt.setDouble(6, deductions);
        stmt.setDouble(7, netSalary);
        stmt.executeUpdate();
    }

    public void updateTotalSalary(int payrunID) throws SQLException {
        String sql = "UPDATE payrun SET totalSalary = (SELECT SUM(netSalary) FROM payrundetail WHERE payrunID = ?) WHERE payrunID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, payrunID);
        stmt.setInt(2, payrunID);
        stmt.executeUpdate();
    }
    
    public Payrun getPayrunSummary(int payrunID) throws SQLException {
        String sql = "SELECT * FROM payrun WHERE payrunID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, payrunID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Payrun payrun = new Payrun();
            payrun.setPayrunID(rs.getInt("payrunID"));
            payrun.setMonth(rs.getInt("month"));
            payrun.setYear(rs.getInt("year"));
            payrun.setCreatedDate(rs.getTimestamp("createdDate"));
            payrun.setStatus(rs.getString("status"));
            payrun.setTotalSalary(rs.getDouble("totalSalary"));
            payrun.setCreatedBy(rs.getInt("createdBy"));
            return payrun;
        }
        return null;
    }

    public List<PayrunDetails> getPayrunDetails(int payrunID) throws SQLException {
        List<PayrunDetails> list = new ArrayList<>();
        String sql = "SELECT pd.*, u.name FROM payrundetail pd JOIN user u ON pd.userID = u.userID WHERE pd.payrunID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, payrunID);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            PayrunDetails detail = new PayrunDetails();
            detail.setDetailID(rs.getInt("detailID"));
            detail.setUsername(rs.getString("name"));
            detail.setBasicSalary(rs.getDouble("basicSalary"));
            detail.setOvertimeHours(rs.getDouble("overtimeHours"));
            detail.setOvertimePay(rs.getDouble("overtimePay"));
            detail.setDeductions(rs.getDouble("deductions"));
            detail.setNetSalary(rs.getDouble("netSalary"));
            list.add(detail);
        }
        return list;
    }
    
    public Payrun getPayrunById(int payrunID) {
        Payrun payrun = null;
        String sql = "SELECT * FROM payrun WHERE payrunID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, payrunID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                payrun = new Payrun();
                payrun.setPayrunID(rs.getInt("payrunID"));
                payrun.setMonth(rs.getInt("month"));
                payrun.setYear(rs.getInt("year"));
                payrun.setCreatedDate(rs.getTimestamp("createdDate"));
                payrun.setStatus(rs.getString("status"));
                payrun.setTotalSalary(rs.getDouble("totalSalary"));
                payrun.setCreatedBy(rs.getInt("createdBy"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payrun;
    }

    public List<Payrun> getAllPayruns() {
        List<Payrun> list = new ArrayList<>();
        String sql = "SELECT * FROM payrun ORDER BY year DESC, month DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Payrun pr = new Payrun();
                pr.setPayrunID(rs.getInt("payrunID"));
                pr.setMonth(rs.getInt("month"));
                pr.setYear(rs.getInt("year"));
                pr.setCreatedDate(rs.getTimestamp("createdDate"));
                pr.setStatus(rs.getString("status"));
                pr.setTotalSalary(rs.getDouble("totalSalary"));
                pr.setCreatedBy(rs.getInt("createdBy"));
                list.add(pr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}

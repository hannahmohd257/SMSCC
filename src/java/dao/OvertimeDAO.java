/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import model.OvertimeRequest;
import util.DBConnection;

public class OvertimeDAO {
    public void submitRequest(OvertimeRequest ot) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO overtimerequest (userID, otDate, startTime, endTime, reason) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ot.getUserID());
            ps.setDate(2, Date.valueOf(ot.getOtDate()));
            ps.setTime(3, Time.valueOf(ot.getStartTime()));
            ps.setTime(4, Time.valueOf(ot.getEndTime()));
            ps.setString(5, ot.getReason());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OvertimeRequest> getPendingRequests() {
        List<OvertimeRequest> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT o.*, u.fullname " +
                         "FROM overtimerequest o " +
                         "JOIN user u ON o.userID = u.userID " +
                         "WHERE o.status = 'Pending'";
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                OvertimeRequest ot = new OvertimeRequest(
                    rs.getInt("overtimeID"),
                    rs.getString("userID"),
                    rs.getDate("otDate").toLocalDate(),
                    rs.getTime("startTime").toLocalTime(),
                    rs.getTime("endTime").toLocalTime(),
                    rs.getString("reason"),
                    rs.getString("status"),
                    rs.getString("fullname")  // Add fullname here
                );
                list.add(ot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void updateStatus(int overtimeID, String status, String reviewedBy, String remarks) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE overtimerequest SET status=?, reviewedBy=?, reviewedDate=NOW(), remarks=? WHERE overtimeID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, reviewedBy);
            ps.setString(3, remarks);
            ps.setInt(4, overtimeID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<OvertimeRequest> getRequestsByUser(String userID) {
        List<OvertimeRequest> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM overtimerequest WHERE userID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OvertimeRequest ot = new OvertimeRequest();
                ot.setOvertimeID(rs.getInt("overtimeID"));
                ot.setUserID(rs.getString("userID"));
                ot.setOtDate(rs.getDate("otDate").toLocalDate());
                ot.setStartTime(rs.getTime("startTime").toLocalTime());
                ot.setEndTime(rs.getTime("endTime").toLocalTime());
                ot.setReason(rs.getString("reason"));
                ot.setStatus(rs.getString("status"));
                list.add(ot);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}


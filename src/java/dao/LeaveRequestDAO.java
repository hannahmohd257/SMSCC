/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
  
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.LeaveRequest;
import util.DBConnection;


public class LeaveRequestDAO {
    
    public List<LeaveRequest> getLeaveRequestsByUser(String userID) {
        List<LeaveRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM leaverequest WHERE userID = ? ORDER BY startDate DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveRequest request = new LeaveRequest();
                request.setRequestID(rs.getInt("requestID"));
                request.setUserID(rs.getString("userID"));
                request.setLeaveType(rs.getString("leaveType"));
                request.setStartDate(rs.getDate("startDate"));
                request.setEndDate(rs.getDate("endDate"));
                request.setReason(rs.getString("reason"));
                request.setStatus(rs.getString("status"));
                request.setRequestDate(rs.getDate("requestDate"));
                request.setDurationType(rs.getString("durationType"));
                request.setFromTime(rs.getTime("fromTime"));
                request.setToTime(rs.getTime("toTime"));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }
    
    //this is for HR to approve/reject leave applications
    public List<LeaveRequest> getPendingLeaveRequests() { 
        List<LeaveRequest> requests = new ArrayList<>();

        // Join with user table to get the fullname
        String sql = "SELECT lr.*, u.fullname " +
                     "FROM leaverequest lr " +
                     "JOIN user u ON lr.userID = u.userID " +
                     "WHERE lr.status = 'Pending' " +
                     "ORDER BY lr.requestDate DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveRequest request = new LeaveRequest();
                request.setRequestID(rs.getInt("requestID"));
                request.setUserID(rs.getString("userID"));
                request.setLeaveType(rs.getString("leaveType"));
                request.setStartDate(rs.getDate("startDate"));
                request.setEndDate(rs.getDate("endDate"));
                request.setReason(rs.getString("reason"));
                request.setStatus(rs.getString("status"));
                request.setRequestDate(rs.getDate("requestDate"));
                request.setDurationType(rs.getString("durationType"));
                request.setFromTime(rs.getTime("fromTime"));
                request.setToTime(rs.getTime("toTime"));
                request.setFullname(rs.getString("fullname")); // From user table
                requests.add(request);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }
}
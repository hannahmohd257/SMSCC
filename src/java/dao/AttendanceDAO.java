/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import model.Attendance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class AttendanceDAO {
    public Attendance getTodayAttendance(int staffID) {
        String query = "SELECT * FROM attendance WHERE staffID = ? AND attendanceDate = CURDATE()";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Attendance(
                    rs.getInt("attendanceID"),
                    rs.getInt("staffID"),
                    rs.getTimestamp("attendanceClockIn"),
                    rs.getTimestamp("attendanceClockOut"),
                    rs.getDate("attendanceDate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Attendance> getAllAttendance(int staffID) {
        String query = "SELECT * FROM attendanceDate WHERE staffID = ? ORDER BY attendanceDate DESC";
        List<Attendance> records = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                records.add(new Attendance(
                    rs.getInt("attendanceID"),
                    rs.getInt("staffID"),
                    rs.getTimestamp("attendanceClockIn"),
                    rs.getTimestamp("attendanceClockOut"),
                    rs.getDate("attendanceDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}

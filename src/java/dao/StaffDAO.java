/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import model.Staff;
import model.User;
import util.DBConnection;

public class StaffDAO {
    private UserDAO userDAO = new UserDAO();
    
    // Save new staff (user + staff)
    public String saveStaff(User user, Staff staff) throws SQLException {
        Connection connection = null;
        PreparedStatement userStatement = null;
        PreparedStatement staffStatement = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false); // Begin transaction

            // ✅ Use same connection to generate ID
            String userID = userDAO.generateNewUserID(user.getRole(), connection);
            user.setUserID(userID);

            // Insert user
            String userQuery = "INSERT INTO user (userID, role, username, fullname, password, email) VALUES (?, ?, ?, ?, ?, ?)";
            userStatement = connection.prepareStatement(userQuery);
            userStatement.setString(1, userID);
            userStatement.setString(2, user.getRole());
            userStatement.setString(3, user.getUsername());
            userStatement.setString(4, user.getFullname());
            userStatement.setString(5, user.getPassword());
            userStatement.setString(6, user.getEmail());

            int userRows = userStatement.executeUpdate();
            if (userRows == 0) {
                throw new SQLException("Inserting user failed. No rows affected.");
            }

            // Insert staff
            String staffQuery = "INSERT INTO staff (userID, staffJoinedDate, staffGender, staffPosition, staffAddress, staffPhoneno, staffMaritalStatus, staffEmpType, staffBank, staffAccNo, staffDOB, basicSalary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            staffStatement = connection.prepareStatement(staffQuery);
            staffStatement.setString(1, userID);
            staffStatement.setDate(2, new java.sql.Date(staff.getStaffJoinedDate().getTime()));
            staffStatement.setString(3, staff.getStaffGender());
            staffStatement.setString(4, staff.getStaffPosition());
            staffStatement.setString(5, staff.getStaffAddress());
            staffStatement.setString(6, staff.getStaffPhoneno());
            staffStatement.setString(7, staff.getStaffMaritalStatus());
            staffStatement.setString(8, staff.getStaffEmpType());
            staffStatement.setString(9, staff.getStaffBank());
            staffStatement.setString(10, staff.getStaffAccNo());
            staffStatement.setDate(11, new java.sql.Date(staff.getStaffDOB().getTime()));
            staffStatement.setDouble(12, staff.getBasicSalary());

            int staffRows = staffStatement.executeUpdate();
            if (staffRows == 0) {
                throw new SQLException("Inserting staff failed. No rows affected.");
            }

            connection.commit(); // ✅ COMMIT both inserts
            return userID;

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            throw e;
        } finally {
            // Clean-up
            if (staffStatement != null) try { staffStatement.close(); } catch (SQLException ex) { System.err.println("Failed to close staffStatement: " + ex.getMessage()); }
            if (userStatement != null) try { userStatement.close(); } catch (SQLException ex) { System.err.println("Failed to close userStatement: " + ex.getMessage()); }
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Reset autocommit
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Failed to close connection: " + ex.getMessage());
                }
            }
        }
    }


    // Update existing staff
    public void updateStaff(Staff staff) throws SQLException {
        String sql = "UPDATE staff SET " +
                     "staffPosition = ?, staffAddress = ?, staffPhoneno = ?, staffMaritalStatus = ?, " +
                     "staffEmpType = ?, staffBank = ?, staffAccNo = ?, staffGender = ?, " +
                     "staffDOB = ?, staffJoinedDate = ?, basicSalary = ? " +
                     "WHERE userID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, staff.getStaffPosition());
            stmt.setString(2, staff.getStaffAddress());
            stmt.setString(3, staff.getStaffPhoneno());
            stmt.setString(4, staff.getStaffMaritalStatus());
            stmt.setString(5, staff.getStaffEmpType());
            stmt.setString(6, staff.getStaffBank());
            stmt.setString(7, staff.getStaffAccNo());
            stmt.setString(8, staff.getStaffGender());
            stmt.setDate(9, new java.sql.Date(staff.getStaffDOB().getTime()));
            stmt.setDate(10, new java.sql.Date(staff.getStaffJoinedDate().getTime()));
            stmt.setDouble(11, staff.getBasicSalary());
            stmt.setString(12, staff.getUserID());

            stmt.executeUpdate();
        }
    }


    // Delete staff by userID
    public void deleteStaff(String userID) throws SQLException {
        String sql = "DELETE FROM staff WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.executeUpdate();
        }
    }



    // Get staff by userID
    public Staff getUserById(String userID) throws SQLException {
        String query = "SELECT * FROM user JOIN staff ON user.userID = staff.userID WHERE user.userID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userID);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractStaffFromResultSet(rs);
                } else {
                    return null;
                }
            }
        }
    }
    
    public Staff getStaffByUserID(String userID) {
        Staff staff = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM staff WHERE userID = ?")) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                staff = new Staff();
                staff.setStaffPosition(rs.getString("staffPosition"));
                staff.setStaffGender(rs.getString("staffGender"));
                staff.setStaffPhoneno(rs.getString("staffPhoneno"));
                staff.setStaffDOB(rs.getDate("staffDOB"));
                staff.setStaffAddress(rs.getString("staffAddress"));
                staff.setStaffJoinedDate(rs.getDate("staffJoinedDate"));
                staff.setStaffMaritalStatus(rs.getString("staffMaritalStatus"));
                staff.setStaffEmpType(rs.getString("staffEmpType"));
                staff.setStaffBank(rs.getString("staffBank"));
                staff.setStaffAccNo(rs.getString("staffAccNo"));
                staff.setBasicSalary(rs.getDouble("basicSalary"));
                // Add any other fields here
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    // Helper method to extract Staff from ResultSet
    private Staff extractStaffFromResultSet(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        User user = new User();

        user.setUserID(rs.getString("userID"));
        user.setRole(rs.getString("role"));
        user.setUsername(rs.getString("username"));
        user.setFullname(rs.getString("fullname"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));

        staff.setStaffJoinedDate(rs.getDate("staffJoinedDate"));
        staff.setStaffGender(rs.getString("staffGender"));
        staff.setStaffPosition(rs.getString("staffPosition"));
        staff.setStaffAddress(rs.getString("staffAddress"));
        staff.setStaffPhoneno(rs.getString("staffPhoneno"));
        staff.setStaffMaritalStatus(rs.getString("staffMaritalStatus"));
        staff.setStaffEmpType(rs.getString("staffEmpType"));
        staff.setStaffBank(rs.getString("staffBank"));
        staff.setStaffAccNo(rs.getString("staffAccNo"));
        staff.setStaffDOB(rs.getDate("staffDOB"));
        staff.setBasicSalary(rs.getDouble("basicSalary"));
        

        return staff;
    }
}

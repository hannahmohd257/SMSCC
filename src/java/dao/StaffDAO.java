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
import model.Staff;
import util.DBConnection;
import static util.DBConnection.getConnection;

public class StaffDAO {

    public Staff getStaffByID(int staffID) {
        Staff staff = null;
        String query = "SELECT * FROM staff WHERE staffID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staffID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffID"));
                staff.setStaffPassword(resultSet.getString("staffPassword"));
                staff.setStaffRole(resultSet.getString("staffRole"));
                
                // Handle nullable fields
                staff.setStaffName(resultSet.getString("staffName"));
                staff.setStaffFullname(resultSet.getString("staffFullname"));
                staff.setStaffEmail(resultSet.getString("staffEmail"));
                staff.setStaffPosition(resultSet.getString("staffPosition"));
                staff.setStaffPhoneno(resultSet.getString("staffPhoneno"));
                staff.setStaffAddress(resultSet.getString("staffAddress"));

                // Handle nullable Date fields safely
                staff.setStaffJoinedDate(resultSet.getDate("staffJoinedDate"));
                staff.setStaffGender(resultSet.getString("staffGender"));
                staff.setStaffDOB(resultSet.getDate("staffDOB"));
                staff.setStaffMaritalStatus(resultSet.getString("staffMaritalStatus"));
                staff.setStaffEmpType(resultSet.getString("staffEmpType"));
                staff.setStaffBank(resultSet.getString("staffBank"));
                staff.setStaffAccNo(resultSet.getString("staffAccNo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public boolean validateStaffCredentials(int staffID, String staffPassword, String staffRole) {
        boolean isValid = false;
        String query = "SELECT * FROM staff WHERE staffID = ? AND staffPassword = ? AND staffRole = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staffID);
            statement.setString(2, staffPassword);
            statement.setString(3, staffRole); // Convert Role to its Integer value
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public List<Staff> getAllEmployees() {
        List<Staff> employees = new ArrayList<>();
        String query = "SELECT staffID, staffFullname, staffEmail, staffPosition FROM staff";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffID"));
                staff.setStaffFullname(resultSet.getString("staffFullname"));
                staff.setStaffEmail(resultSet.getString("staffEmail"));
                staff.setStaffPosition(resultSet.getString("staffPosition"));
                employees.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    public int saveStaff(Staff staff) throws SQLException {
        String query = "INSERT INTO staff (staffFullname, staffName, staffJoinedDate, staffGender, staffPosition, staffAddress, staffPhoneno, staffEmail, staffMaritalStatus, staffEmpType, staffBank, staffAccNo, staffDOB, staffPassword, staffRole) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set the parameters from the staff object
            statement.setString(1, staff.getStaffFullname());
            statement.setString(2, staff.getStaffName());
            statement.setDate(3, staff.getStaffJoinedDate() != null ? new java.sql.Date(staff.getStaffJoinedDate().getTime()) : null);
            statement.setString(4, staff.getStaffGender());
            statement.setString(5, staff.getStaffPosition());
            statement.setString(6, staff.getStaffAddress());
            statement.setString(7, staff.getStaffPhoneno());
            statement.setString(8, staff.getStaffEmail());
            statement.setString(9, staff.getStaffMaritalStatus());
            statement.setString(10, staff.getStaffEmpType());
            statement.setString(11, staff.getStaffBank());
            statement.setString(12, staff.getStaffAccNo());
            statement.setDate(13, staff.getStaffDOB() != null ? new java.sql.Date(staff.getStaffDOB().getTime()) : null);
            statement.setString(14, staff.getStaffPassword());
            statement.setString(15, staff.getStaffRole());

            // Execute the query
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to insert staff, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated staffID
                } else {
                    throw new SQLException("Failed to insert staff, no ID obtained.");
                }
            }
        }
    }
    
    public boolean updateStaff(Staff staff) {
        String query = "UPDATE staff SET staffFullname = ?, staffName = ?, staffJoinedDate = ?, staffGender = ?, " +
                       "staffPosition = ?, staffAddress = ?, staffPhoneno = ?, staffEmail = ?, staffMaritalStatus = ?, " +
                       "staffEmpType = ?, staffBank = ?, staffAccNo = ?, staffDOB = ?, staffPassword = ?, staffRole = ? " +
                       "WHERE staffID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Prepare statement with correct data types
            stmt.setString(1, staff.getStaffFullname());
            stmt.setString(2, staff.getStaffName());

            // Assuming staffJoinedDate is a java.util.Date object
            stmt.setDate(3, new java.sql.Date(staff.getStaffJoinedDate().getTime()));

            stmt.setString(4, staff.getStaffGender());
            stmt.setString(5, staff.getStaffPosition());
            stmt.setString(6, staff.getStaffAddress());
            stmt.setString(7, staff.getStaffPhoneno());
            stmt.setString(8, staff.getStaffEmail());
            stmt.setString(9, staff.getStaffMaritalStatus());
            stmt.setString(10, staff.getStaffEmpType());
            stmt.setString(11, staff.getStaffBank());
            stmt.setString(12, staff.getStaffAccNo());

            // Assuming staffDOB is a java.util.Date object
            stmt.setDate(13, new java.sql.Date(staff.getStaffDOB().getTime()));

            stmt.setString(14, staff.getStaffPassword());
            stmt.setString(15, staff.getStaffRole());
            stmt.setInt(16, staff.getStaffID());

            // Execute update and check if any rows were affected
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    
    public boolean deleteStaff(int staffID) {
        Connection connection = null;
        PreparedStatement salaryStmt = null;
        PreparedStatement staffStmt = null;
        boolean isDeleted = false;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            String deleteSalarySQL = "DELETE FROM salary WHERE staffID = ?";
            salaryStmt = connection.prepareStatement(deleteSalarySQL);
            salaryStmt.setInt(1, staffID);
            salaryStmt.executeUpdate();

            String deleteStaffSQL = "DELETE FROM staff WHERE staffID = ?";
            staffStmt = connection.prepareStatement(deleteStaffSQL);
            staffStmt.setInt(1, staffID);
            int rowsAffected = staffStmt.executeUpdate();

            if (rowsAffected > 0) {
                isDeleted = true;
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            DBConnection.close(salaryStmt, connection);
            DBConnection.close(staffStmt);
        }

        return isDeleted;
    }
}
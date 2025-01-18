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
import java.util.Date;
import java.util.List;
import model.Role;
import model.Staff;
import util.DBConnection;

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
                
                // Convert role from Integer to Role enum, if possible
                staff.setStaffRole(Role.fromValue(resultSet.getInt("staffRole")));
                
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

    public boolean validateStaffCredentials(int staffID, String staffPassword, Role staffRole) {
        boolean isValid = false;
        String query = "SELECT * FROM staff WHERE staffID = ? AND staffPassword = ? AND staffRole = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staffID);
            statement.setString(2, staffPassword);
            statement.setInt(3, staffRole.getValue()); // Convert Role to its Integer value
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
            statement.setString(15, staff.getStaffRole() != null ? staff.getStaffRole().name() : Role.GENERAL_STAFF.name());  // Convert enum to string // Convert enum to string

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
    
}

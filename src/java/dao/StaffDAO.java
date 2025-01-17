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
                staff.setRole(Role.fromValue(resultSet.getInt("role")));
                
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

    public boolean validateStaffCredentials(int staffID, String staffPassword, Role role) {
        boolean isValid = false;
        String query = "SELECT * FROM staff WHERE staffID = ? AND staffPassword = ? AND role = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staffID);
            statement.setString(2, staffPassword);
            statement.setInt(3, role.getValue()); // Convert Role to its Integer value
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
}

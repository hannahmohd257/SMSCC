/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Salary;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryDAO {

    // Method to add salary details for an employee
    public boolean addSalary(Salary salary) {
        String query = "INSERT INTO Salary (userID, salaryBasic, salaryDeduction, salaryOvtRate) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, salary.getUserID());
            statement.setDouble(2, salary.getSalaryBasic());
            statement.setDouble(3, salary.getSalaryDeduction());
            statement.setDouble(4, salary.getSalaryOvtRate());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve salary information for a specific employee
    public Salary getSalaryByUserID(String userID) {
        Salary salary = null;
        String query = "SELECT * FROM Salary WHERE userID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                salary = new Salary();
                salary.setSalaryID(resultSet.getInt("salaryID"));
                salary.setUserID(resultSet.getString("userID"));
                salary.setSalaryBasic(resultSet.getDouble("salaryBasic"));
                salary.setSalaryDeduction(resultSet.getDouble("salaryDeduction"));
                salary.setSalaryOvtRate(resultSet.getDouble("salaryOvtRate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salary;
    }

    // Method to update salary details for an employee
    public boolean updateSalary(Salary salary) {
        String query = "UPDATE Salary SET basicSalary = ?, deductions = ?, overtimeRate = ? WHERE userID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, salary.getSalaryBasic());
            statement.setDouble(2, salary.getSalaryDeduction());
            statement.setDouble(3, salary.getSalaryOvtRate());
            statement.setString(4, salary.getUserID());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

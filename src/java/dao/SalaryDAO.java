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
        String query = "INSERT INTO Salary (staffID, salaryBasic, salaryDeduction, salaryOvtRate) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, salary.getStaffID());
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
    public Salary getSalaryByStaffID(int staffID) {
        Salary salary = null;
        String query = "SELECT * FROM Salary WHERE staffID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staffID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                salary = new Salary();
                salary.setSalaryID(resultSet.getInt("salaryID"));
                salary.setStaffID(resultSet.getInt("staffID"));
                salary.setSalaryBasic(resultSet.getDouble("salaryBasic"));
                salary.setSalaryDeduction(resultSet.getDouble("salaryDeduction"));
                salary.setSalaryOvtRate(resultSet.getDouble("salaryOvtRate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salary;
    }
//
//    // Method to retrieve all salaries (could be used for an admin view)
//    public List<Salary> getAllSalaries() {
//        List<Salary> salaries = new ArrayList<>();
//        String query = "SELECT * FROM Salary";
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//            while (resultSet.next()) {
//                Salary salary = new Salary();
//                salary.setSalaryID(resultSet.getInt("salaryID"));
//                salary.setStaffID(resultSet.getInt("staffID"));
//                salary.setBasicSalary(resultSet.getDouble("basicSalary"));
//                salary.setDeductions(resultSet.getDouble("deductions"));
//                salary.setOvertimeRate(resultSet.getDouble("overtimeRate"));
//                salaries.add(salary);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return salaries;
//    }

    // Method to update salary details for an employee
    public boolean updateSalary(Salary salary) {
        String query = "UPDATE Salary SET basicSalary = ?, deductions = ?, overtimeRate = ? WHERE staffID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, salary.getSalaryBasic());
            statement.setDouble(2, salary.getSalaryDeduction());
            statement.setDouble(3, salary.getSalaryOvtRate());
            statement.setInt(4, salary.getStaffID());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author user
 */
public class Salary {
    private int salaryID;
    private String userID; // Foreign key from Staff
    private double salaryBasic;
    private double salaryDeduction;
    private double salaryOvtRate;

    // Constructor
    public Salary(int salaryID, String userID, double salaryBasic, double salaryDeduction, double salaryOvtRate) {
        this.salaryID = salaryID;
        this.userID = userID;
        this.salaryBasic = salaryBasic;
        this.salaryDeduction = salaryDeduction;
        this.salaryOvtRate = salaryOvtRate;
    }

    // Default constructor
    public Salary() {}

    // Getters and Setters
    public int getSalaryID() {
        return salaryID;
    }

    public void setSalaryID(int salaryID) {
        this.salaryID = salaryID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getSalaryBasic() {
        return salaryBasic;
    }

    public void setSalaryBasic(double salaryBasic) {
        this.salaryBasic = salaryBasic;
    }

    public double getSalaryDeduction() {
        return salaryDeduction;
    }

    public void setSalaryDeduction(double salaryDeduction) {
        this.salaryDeduction = salaryDeduction;
    }

    public double getSalaryOvtRate() {
        return salaryOvtRate;
    }

    public void setSalaryOvtRate(double salaryOvtRate) {
        this.salaryOvtRate = salaryOvtRate;
    }

    // toString method
    @Override
    public String toString() {
        return "Salary{" +
                "salaryID=" + salaryID +
                ", userID=" + userID +
                ", basicSalary=" + salaryBasic +
                ", deductions=" + salaryDeduction +
                ", overtimeRate=" + salaryOvtRate +
                '}';
    }
}


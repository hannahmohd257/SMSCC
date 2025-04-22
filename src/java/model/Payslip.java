/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;


public class Payslip {
    private int payslipID;
    private int staffID;
    private double payslipSalaryAmount;
    private double payslipDeductionAmount;
    private double payslipOvertimePay;
    private double payslipNetPay;	
    private String payslipMonth;
    private Timestamp payslipCreatedAt;	

    /**
     * @return the payslipID
     */
    public int getPayslipID() {
        return payslipID;
    }

    /**
     * @param payslipID the payslipID to set
     */
    public void setPayslipID(int payslipID) {
        this.payslipID = payslipID;
    }

    /**
     * @return the staffID
     */
    public int getStaffID() {
        return staffID;
    }

    /**
     * @param staffID the staffID to set
     */
    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    /**
     * @return the payslipSalaryAmount
     */
    public double getPayslipSalaryAmount() {
        return payslipSalaryAmount;
    }

    /**
     * @param payslipSalaryAmount the payslipSalaryAmount to set
     */
    public void setPayslipSalaryAmount(double payslipSalaryAmount) {
        this.payslipSalaryAmount = payslipSalaryAmount;
    }

    /**
     * @return the payslipDeductionAmount
     */
    public double getPayslipDeductionAmount() {
        return payslipDeductionAmount;
    }

    /**
     * @param payslipDeductionAmount the payslipDeductionAmount to set
     */
    public void setPayslipDeductionAmount(double payslipDeductionAmount) {
        this.payslipDeductionAmount = payslipDeductionAmount;
    }

    /**
     * @return the payslipOvertimePay
     */
    public double getPayslipOvertimePay() {
        return payslipOvertimePay;
    }

    /**
     * @param payslipOvertimePay the payslipOvertimePay to set
     */
    public void setPayslipOvertimePay(double payslipOvertimePay) {
        this.payslipOvertimePay = payslipOvertimePay;
    }

    /**
     * @return the payslipNetPay
     */
    public double getPayslipNetPay() {
        return payslipNetPay;
    }

    /**
     * @param payslipNetPay the payslipNetPay to set
     */
    public void setPayslipNetPay(double payslipNetPay) {
        this.payslipNetPay = payslipNetPay;
    }

    /**
     * @return the payslipMonth
     */
    public String getPayslipMonth() {
        return payslipMonth;
    }

    /**
     * @param payslipMonth the payslipMonth to set
     */
    public void setPayslipMonth(String payslipMonth) {
        this.payslipMonth = payslipMonth;
    }

    /**
     * @return the payslipCreatedAt
     */
    public Timestamp getPayslipCreatedAt() {
        return payslipCreatedAt;
    }

    /**
     * @param payslipCreatedAt the payslipCreatedAt to set
     */
    public void setPayslipCreatedAt(Timestamp payslipCreatedAt) {
        this.payslipCreatedAt = payslipCreatedAt;
    }

    
}
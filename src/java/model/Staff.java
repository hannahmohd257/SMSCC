/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.util.Date;

public class Staff {
    private String userID;
    private String staffPosition;
    private String staffPhoneno;
    private String staffAddress;
    private Date staffJoinedDate;
    private String staffGender;
    private Date staffDOB;
    private String staffMaritalStatus;
    private String staffEmpType;
    private String staffBank;
    private String staffAccNo;
    private double basicSalary;
    private User user; // userID, username, fullname, email, role are inside here

    // Constructor
    public Staff(String userID,Date staffJoinedDate, String staffPosition, String staffPhoneno, 
             Date staffDOB, String staffAddress, String staffGender, String staffMaritalStatus, 
             String staffEmpType, String staffBank, String staffAccNo, double basicSalary) {
    this.userID = userID;
    this.staffJoinedDate = staffJoinedDate;
    this.staffPosition = staffPosition;
    this.staffPhoneno = staffPhoneno;
    this.staffDOB = staffDOB;
    this.staffAddress = staffAddress;
    this.staffGender = staffGender;
    this.staffMaritalStatus = staffMaritalStatus;
    this.staffEmpType = staffEmpType;
    this.staffBank = staffBank;
    this.staffAccNo = staffAccNo;
    this.basicSalary = basicSalary;
    }

    // Default constructor
    public Staff() {}

    // Getters and Setters for all fields, including the new ones
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getStaffPosition() {
        return staffPosition;
    }

    public void setStaffPosition(String staffPosition) {
        this.staffPosition = staffPosition;
    }

    public String getStaffPhoneno() {
        return staffPhoneno;
    }

    public void setStaffPhoneno(String staffPhoneno) {
        this.staffPhoneno = staffPhoneno;
    }

    public String getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(String staffAddress) {
        this.staffAddress = staffAddress;
    }

    // Getters and Setters for the new fields
    public Date getStaffJoinedDate() {
        return staffJoinedDate;
    }

    public void setStaffJoinedDate(Date staffJoinedDate) {
        this.staffJoinedDate = staffJoinedDate;
    }

    public String getStaffGender() {
        return staffGender;
    }

    public void setStaffGender(String staffGender) {
        this.staffGender = staffGender;
    }

    public Date getStaffDOB() {
        return staffDOB;
    }

    public void setStaffDOB(Date staffDOB) {
        this.staffDOB = staffDOB;
    }

    public String getStaffMaritalStatus() {
        return staffMaritalStatus;
    }

    public void setStaffMaritalStatus(String staffMaritalStatus) {
        this.staffMaritalStatus = staffMaritalStatus;
    }

    public String getStaffEmpType() {
        return staffEmpType;
    }

    public void setStaffEmpType(String staffEmpType) {
        this.staffEmpType = staffEmpType;
    }

    public String getStaffBank() {
        return staffBank;
    }

    public void setStaffBank(String staffBank) {
        this.staffBank = staffBank;
    }

    public String getStaffAccNo() {
        return staffAccNo;
    }

    public void setStaffAccNo(String staffAccNo) {
        this.staffAccNo = staffAccNo;
    }
    
    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    // Getter and setter for `user`
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    // toString method (updated with new fields)
    @Override
    public String toString() {
        return "Staff{" +
                "userID=" + userID +
                ", staffPosition='" + staffPosition + '\'' +
                ", staffPhoneno='" + staffPhoneno + '\'' +
                ", staffAddress='" + staffAddress + '\'' +
                ", staffJoinedDate=" + staffJoinedDate +
                ", staffGender='" + staffGender + '\'' +
                ", staffDOB=" + staffDOB +
                ", staffMaritalStatus='" + staffMaritalStatus + '\'' +
                ", staffEmpType='" + staffEmpType + '\'' +
                ", staffBank='" + staffBank + '\'' +
                ", staffAccNo='" + staffAccNo + '\'' +
                ", basicSalary='" + basicSalary + '\'' +
                '}';
    }
}

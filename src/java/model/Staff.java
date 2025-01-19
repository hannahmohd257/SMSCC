/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.util.Date;

public class Staff {
    private int staffID;
    private String staffPassword;
    private String staffRole;
    private String staffName;
    private String staffFullname;
    private String staffEmail;
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

    // Constructor
    public Staff(int staffID, String staffPassword, String staffRole, String staffName, String staffFullname, 
                 String staffEmail, String staffPosition, String staffPhoneno, String staffAddress, 
                 Date staffJoinedDate, String staffGender, Date staffDOB, String staffMaritalStatus, 
                 String staffEmpType, String staffBank, String staffAccNo) {
        this.staffID = staffID;
        this.staffPassword = staffPassword;
        this.staffRole = staffRole;
        this.staffName = staffName;
        this.staffFullname = staffFullname;
        this.staffEmail = staffEmail;
        this.staffPosition = staffPosition;
        this.staffPhoneno = staffPhoneno;
        this.staffAddress = staffAddress;
        this.staffJoinedDate = staffJoinedDate;
        this.staffGender = staffGender;
        this.staffDOB = staffDOB;
        this.staffMaritalStatus = staffMaritalStatus;
        this.staffEmpType = staffEmpType;
        this.staffBank = staffBank;
        this.staffAccNo = staffAccNo;
    }

    // Default constructor
    public Staff() {}

    // Getters and Setters for all fields, including the new ones
    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffFullname() {
        return staffFullname;
    }

    public void setStaffFullname(String staffFullname) {
        this.staffFullname = staffFullname;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
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

    // toString method (updated with new fields)
    @Override
    public String toString() {
        return "Staff{" +
                "staffID=" + staffID +
                ", staffPassword='" + staffPassword + '\'' +
                ", staffRole='" + staffRole +
                ", staffName='" + staffName + '\'' +
                ", staffFullname='" + staffFullname + '\'' +
                ", staffEmail='" + staffEmail + '\'' +
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
                '}';
    }
}

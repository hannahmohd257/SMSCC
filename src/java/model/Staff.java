/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class Staff {
    private int staffID;
    private String staffPassword;
    private Role role;
    private String staffName;
    private String staffFullname;
    private String staffEmail;
    private String staffPosition;
    private String staffPhoneno;
    private String staffAddress;

    // Constructor
    public Staff(int staffID, String staffPassword, Role role, String staffName, String staffFullname, String staffEmail, String staffPosition, String staffPhoneno, String staffAddress) {
        this.staffID = staffID;
        this.staffPassword = staffPassword;
        this.role = role;
        this.staffName = staffName;
        this.staffFullname = staffFullname;
        this.staffEmail = staffEmail;
        this.staffPosition = staffPosition;
        this.staffPhoneno = staffPhoneno;
        this.staffAddress = staffAddress;
    }

    // Default constructor
    public Staff() {}

    // Getters and Setters
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    // toString method
    @Override
    public String toString() {
        return "Staff{" +
                "staffID=" + staffID +
                ", staffPassword='" + staffPassword + '\'' +
                ", role='" + role + '\'' +
                ", staffName='" + staffName + '\'' +
                ", staffFullname='" + staffFullname + '\'' +
                ", staffEmail='" + staffEmail + '\'' +
                ", staffPosition='" + staffPosition + '\'' +
                ", staffPhoneno='" + staffPhoneno + '\'' +
                ", staffAddress='" + staffAddress + '\'' +
                '}';
    }
}

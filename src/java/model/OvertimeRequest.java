/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class OvertimeRequest {
    private int overtimeID;
    private String userID;
    private LocalDate otDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String reason;
    private String status;
    private String reviewedBy;
    private String remarks;
    private String fullname;

    // Constructors
    public OvertimeRequest() {}

    // For submission
    public OvertimeRequest(String userID, LocalDate otDate, LocalTime startTime, LocalTime endTime, String reason) {
        this.userID = userID;
        this.otDate = otDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.status = "Pending";
    }

    // For retrieval
    public OvertimeRequest(int overtimeID, String userID, LocalDate otDate, LocalTime startTime, LocalTime endTime, String reason, String status, String fullname) {
        this.overtimeID = overtimeID;
        this.userID = userID;
        this.otDate = otDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.status = status;
        this.fullname = fullname;
    }

    // Getters and Setters
    public int getOvertimeID() {
        return overtimeID;
    }

    public void setOvertimeID(int overtimeID) {
        this.overtimeID = overtimeID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDate getOtDate() {
        return otDate;
    }

    public void setOtDate(LocalDate otDate) {
        this.otDate = otDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

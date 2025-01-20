/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.sql.Date;

public class Attendance {
    private int attendanceID;
    private int staffID;
    private Timestamp attendanceClockIn;
    private Timestamp attendanceClockOut;
    private Date attendanceDate;

    public Attendance(int attendanceID, int staffID, Timestamp attendanceClockIn, Timestamp attendanceClockOut, Date attendanceDate) {
        this.attendanceID = attendanceID;
        this.staffID = staffID;
        this.attendanceClockIn = attendanceClockIn;
        this.attendanceClockOut = attendanceClockOut;
        this.attendanceDate = attendanceDate;
    }


    public int getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public Timestamp getAttendanceClockIn() {
        return attendanceClockIn;
    }

    /**
     * @param attendanceClockIn the attendanceClockIn to set
     */
    public void setAttendanceClockIn(Timestamp attendanceClockIn) {
        this.attendanceClockIn = attendanceClockIn;
    }

    /**
     * @return the attendanceClockOut
     */
    public Timestamp getAttendanceClockOut() {
        return attendanceClockOut;
    }

    /**
     * @param attendanceClockOut the attendanceClockOut to set
     */
    public void setAttendanceClockOut(Timestamp attendanceClockOut) {
        this.attendanceClockOut = attendanceClockOut;
    }

    /**
     * @return the attendanceDate
     */
    public Date getAttendanceDate() {
        return attendanceDate;
    }

    /**
     * @param attendanceDate the attendanceDate to set
     */
    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
}

   
package servlet;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.LeaveRequest;
import util.DBConnection;

public class LeaveRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("submit".equals(action)) {
            handleLeaveSubmission(request, response);
        } else if ("view".equals(action)) {
            handleViewRequests(request, response);
        }
    }

    private void handleLeaveSubmission(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String durationType = request.getParameter("durationType");
        String leaveType = request.getParameter("leaveType");
        String reason = request.getParameter("reason");
        String startDateStr;
        String endDateStr = null;

        if ("partial".equals(durationType)) {
            startDateStr = request.getParameter("partialDate");
        } else {
            startDateStr = request.getParameter("startDate");
            endDateStr = request.getParameter("endDate");
        }

        String fromTimeStr = request.getParameter("fromTime");
        String toTimeStr = request.getParameter("toTime");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection conn = DBConnection.getConnection()) {

            java.sql.Date startDate;
            java.sql.Date endDate;

            try {
                startDate = new java.sql.Date(dateFormat.parse(startDateStr).getTime());

                if (endDateStr != null && !endDateStr.isEmpty()) {
                    endDate = new java.sql.Date(dateFormat.parse(endDateStr).getTime());
                } else {
                    endDate = startDate; // For partial leave, same date
                }
            } catch (ParseException e) {
                session.setAttribute("leaveMessage", "Invalid date format.");
                response.sendRedirect("staffLeaveRequest.jsp");
                return;
            }

            Time fromTime = null;
            Time toTime = null;

            if ("partial".equals(durationType)) {
                try {
                    fromTime = Time.valueOf(fromTimeStr + ":00");
                    toTime = Time.valueOf(toTimeStr + ":00");
                } catch (IllegalArgumentException e) {
                    session.setAttribute("leaveMessage", "Invalid time format.");
                    response.sendRedirect("staffLeaveRequest.jsp");
                    return;
                }
            }

            String sql = "INSERT INTO leaverequest (userID, leaveType, startDate, endDate, reason, status, requestDate, durationType, fromTime, toTime) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ps.setString(2, leaveType);
            ps.setDate(3, startDate);
            ps.setDate(4, endDate);
            ps.setString(5, reason);
            ps.setString(6, "Pending");
            ps.setDate(7, new java.sql.Date(System.currentTimeMillis()));
            ps.setString(8, durationType);

            if ("partial".equals(durationType)) {
                ps.setTime(9, fromTime);
                ps.setTime(10, toTime);
            } else {
                ps.setNull(9, Types.TIME);
                ps.setNull(10, Types.TIME);
            }

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                session.setAttribute("leaveMessage", "Leave request submitted successfully.");
            } else {
                session.setAttribute("leaveMessage", "Failed to submit leave request.");
            }

            response.sendRedirect("staffDashboard.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("leaveMessage", "Database error: " + e.getMessage());
            response.sendRedirect("staffLeaveRequest.jsp");
        }
    }

    private void handleViewRequests(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<LeaveRequest> leaveRequests = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            String sql = "SELECT * FROM leaverequest WHERE userID = ? ORDER BY requestDate DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setLeaveType(rs.getString("leaveType"));
                lr.setStartDate(rs.getDate("startDate"));
                lr.setEndDate(rs.getDate("endDate"));
                lr.setReason(rs.getString("reason"));
                lr.setStatus(rs.getString("status"));
                lr.setRequestDate(rs.getDate("requestDate"));
                lr.setDurationType(rs.getString("durationType"));
                lr.setFromTime(rs.getTime("fromTime"));
                lr.setToTime(rs.getTime("toTime"));
                leaveRequests.add(lr);
            }

            request.setAttribute("requests", leaveRequests);
            request.getRequestDispatcher("staffDashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("leaveMessage", "Error loading leave history.");
            response.sendRedirect("staffLeaveRequest.jsp");
        }
    }
}


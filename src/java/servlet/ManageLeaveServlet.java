
package servlet;

import dao.LeaveRequestDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.LeaveRequest;
import util.DBConnection;

public class ManageLeaveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if (role == null || !role.equals("Human Resource")) {
            response.sendRedirect("login.jsp");
            return;
        }

        LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();
        List<LeaveRequest> pendingRequests = leaveRequestDAO.getPendingLeaveRequests(); 


        try (Connection conn = DBConnection.getConnection()) {
            
            String sql = "SELECT l.*, u.fullname FROM leaverequest l JOIN user u ON l.userID = u.userID " +
                         "WHERE l.status = 'Pending' ORDER BY l.requestDate DESC";


            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setRequestID(rs.getInt("requestID"));
                lr.setUserID(rs.getString("userID"));
                lr.setLeaveType(rs.getString("leaveType"));
                lr.setStartDate(rs.getDate("startDate"));
                lr.setEndDate(rs.getDate("endDate"));
                lr.setReason(rs.getString("reason"));
                lr.setStatus(rs.getString("status"));
                lr.setRequestDate(rs.getDate("requestDate"));
                lr.setDurationType(rs.getString("durationType"));
                lr.setFromTime(rs.getTime("fromTime"));
                lr.setToTime(rs.getTime("toTime"));
                lr.setFullname(rs.getString("fullname")); 
                pendingRequests.add(lr);
            }

            request.setAttribute("pendingRequests", pendingRequests);
            request.getRequestDispatcher("hrLeaveApproval.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if (role == null || !role.equals("Human Resource")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        int requestID = Integer.parseInt(request.getParameter("requestID"));

        if (!"approve".equals(action) && !"reject".equals(action)) {
            response.sendRedirect("ManageLeaveServlet");
            return;
        }

        String newStatus = "approve".equals(action) ? "Approved" : "Rejected";

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE leaverequest SET status = ? WHERE requestID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setInt(2, requestID);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                session.setAttribute("hrLeaveMessage", "Leave request has been " + newStatus.toLowerCase() + ".");
            } else {
                session.setAttribute("hrLeaveMessage", "Failed to update leave request.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("hrLeaveMessage", "Database error: " + e.getMessage());
        }

        response.sendRedirect("ManageLeaveServlet");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddEmployeeServlet extends HttpServlet {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve the step from the form
        String step = request.getParameter("step");

        // Handle Step 1: Basics
        if ("1".equals(step)) {
            String staffFullname = request.getParameter("staffFullname");
            String staffName = request.getParameter("staffName");
            String staffJoinedDateStr = request.getParameter("staffJoinedDate"); // String representation
            String staffGender = request.getParameter("staffGender");
            String staffPosition = request.getParameter("staffPosition");

            // Parse staffJoinedDate from String to Date
            Date staffJoinedDate = parseDate(staffJoinedDateStr);
            session.setAttribute("staffFullname", staffFullname);
            session.setAttribute("staffName", staffName);
            session.setAttribute("staffJoinedDate", staffJoinedDate);
            session.setAttribute("staffGender", staffGender);
            session.setAttribute("staffPosition", staffPosition);
            response.sendRedirect("foAddEmployee2.jsp"); // Navigate to Step 2
        }

        // Handle Step 3: Personal Info
        if ("3".equals(step)) {
            String staffDOBStr = request.getParameter("staffDOB"); // String representation
            String staffAddress = request.getParameter("staffAddress");
            String staffPhoneNo = request.getParameter("staffPhoneNo");
            String staffEmail = request.getParameter("staffEmail");
            String staffMaritalStatus = request.getParameter("staffMaritalStatus");
            String staffEmpType = request.getParameter("staffEmpType");

            // Parse staffDOB from String to Date
            Date staffDOB = parseDate(staffDOBStr);
            session.setAttribute("staffDOB", staffDOB);
            session.setAttribute("staffAddress", staffAddress);
            session.setAttribute("staffPhoneNo", staffPhoneNo);
            session.setAttribute("staffEmail", staffEmail);
            session.setAttribute("staffMaritalStatus", staffMaritalStatus);
            session.setAttribute("staffEmpType", staffEmpType);
            response.sendRedirect("foAddEmployee4.jsp"); // Navigate to Step 4
        }

        // Handle Step 4: Payment Info
        if ("4".equals(step)) {
            String staffBank = request.getParameter("staffBank");
            String staffAccNo = request.getParameter("staffAccNo");
            session.setAttribute("staffBank", staffBank);
            session.setAttribute("staffAccNo", staffAccNo);

            // Save all data to the database
            saveToDatabase(session);
            response.sendRedirect("success.jsp"); // Redirect to a success page
        }
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null; // Handle empty or null date strings
        }
        try {
            return DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parse errors gracefully
        }
    }

    private void saveToDatabase(HttpSession session) {
        // Code to save data to the database using session attributes
        // Example: Convert Date to SQL Date if needed before saving
        java.util.Date staffDOB = (java.util.Date) session.getAttribute("staffDOB");
        java.util.Date staffJoinedDate = (java.util.Date) session.getAttribute("staffJoinedDate");

        if (staffDOB != null) {
            java.sql.Date sqlStaffDOB = new java.sql.Date(staffDOB.getTime());
            // Save sqlStaffDOB to the database
        }

        if (staffJoinedDate != null) {
            java.sql.Date sqlStaffJoinedDate = new java.sql.Date(staffJoinedDate.getTime());
            // Save sqlStaffJoinedDate to the database
        }
    }
}

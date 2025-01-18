/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.StaffDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Role;
import model.Staff;

public class AddEmployeeServlet extends HttpServlet {

    private StaffDAO staffDAO = new StaffDAO();  // Assuming you have a StaffDAO for database interaction

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the step parameter from the form submission
        String step = request.getParameter("step");

        // Get session object
        HttpSession session = request.getSession();

        // Process the form based on the current step
        if ("1".equals(step)) {
            // Step 1: Capture general info and save it to the session
            String staffFullname = request.getParameter("staffFullname");
            String staffName = request.getParameter("staffName");
            String staffJoinedDate = request.getParameter("staffJoinedDate");
            String staffGender = request.getParameter("staffGender");
            String staffPosition = request.getParameter("staffPosition");

            // Save data to session
            session.setAttribute("staffFullname", staffFullname);
            session.setAttribute("staffName", staffName);
            session.setAttribute("staffJoinedDate", staffJoinedDate);
            session.setAttribute("staffGender", staffGender);
            session.setAttribute("staffPosition", staffPosition);

            // Redirect to next form step
            response.sendRedirect("foAddEmployee2.jsp");
        } else if ("2".equals(step)) {
            // Step 2: Capture personal info and save it to the session
            String salaryBasic = request.getParameter("salaryBasic");
            String salaryDeduction = request.getParameter("salaryDeduction");
            String salaryOvtRate = request.getParameter("salaryOvtRate");

            // Save data to session
            session.setAttribute("salaryBasic", salaryBasic);
            session.setAttribute("salaryDeduction", salaryDeduction);
            session.setAttribute("salaryOvtRate", salaryOvtRate);  // Corrected the mistake here

            // Redirect to next form step
            response.sendRedirect("foAddEmployee3.jsp");
        } else if ("3".equals(step)) {
            // Step 3: Capture role, password, etc., and save it to the session
            String staffRole = request.getParameter("staffRole");
            String staffPassword = request.getParameter("staffPassword");
            String staffDOB = request.getParameter("staffDOB");
            String staffAddress = request.getParameter("staffAddress");
            String staffPhoneNo = request.getParameter("staffPhoneNo");
            String staffEmail = request.getParameter("staffEmail");
            String staffMaritalStatus = request.getParameter("staffMaritalStatus");
            String staffEmpType = request.getParameter("staffEmpType");

            // Save data to session
            session.setAttribute("staffRole", staffRole);
            session.setAttribute("staffPassword", staffPassword);
            session.setAttribute("staffDOB", staffDOB);
            session.setAttribute("staffAddress", staffAddress);
            session.setAttribute("staffPhoneNo", staffPhoneNo);
            session.setAttribute("staffEmail", staffEmail);
            session.setAttribute("staffMaritalStatus", staffMaritalStatus);
            session.setAttribute("staffEmpType", staffEmpType);

            // Redirect to next form step
            response.sendRedirect("foAddEmployee4.jsp");
        } else if ("4".equals(step)) {
            // Step 4: Capture bank details and save it to the session
            String staffBank = request.getParameter("staffBank");
            String staffAccNo = request.getParameter("staffAccNo");

            // Save data to session
            session.setAttribute("staffBank", staffBank);
            session.setAttribute("staffAccNo", staffAccNo);

            try {
                // Convert the date strings to Date objects
                String staffJoinedDate = (String) session.getAttribute("staffJoinedDate");
                String staffDOB = (String) session.getAttribute("staffDOB");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date joinedDate = dateFormat.parse(staffJoinedDate); // Parse staffJoinedDate
                Date dob = dateFormat.parse(staffDOB); // Parse staffDOB

                // Create the new employee
                Staff newEmployee = new Staff(
                        0,  // Assuming staffID is auto-generated by the database
                        (String) session.getAttribute("staffPassword"),
                        Role.valueOf((String) session.getAttribute("staffRole")),  // Ensure Role enum matches
                        (String) session.getAttribute("staffName"),
                        (String) session.getAttribute("staffFullname"),
                        (String) session.getAttribute("staffEmail"),
                        (String) session.getAttribute("staffPosition"),
                        (String) session.getAttribute("staffPhoneNo"),
                        (String) session.getAttribute("staffAddress"),
                        joinedDate,  // Parsed date
                        (String) session.getAttribute("staffGender"),
                        dob,  // Parsed date
                        (String) session.getAttribute("staffMaritalStatus"),
                        (String) session.getAttribute("staffEmpType"),
                        staffBank,
                        staffAccNo
                );

                // Save the new employee to the database
                boolean success = staffDAO.saveStaff(newEmployee);

                if (success) {
                    // Success, redirect to the success page
                    session.setAttribute("message", "Employee added successfully!");
                    session.invalidate();  // Clean up session
                    response.sendRedirect("employeeSuccess.jsp");
                } else {
                    // Failure, redirect to the error page
                    session.setAttribute("message", "Error: Employee could not be added.");
                    response.sendRedirect("employeeError.jsp");
                }

            } catch (ParseException e) {
                // Handle potential errors from invalid date parsing
                e.printStackTrace();
                session.setAttribute("message", "Invalid date format provided: " + e.getMessage());
                response.sendRedirect("employeeError.jsp");
            } catch (SQLException e) {
                // Handle potential database errors
                e.printStackTrace();
                session.setAttribute("message", "Error processing the employee data: " + e.getMessage());
                response.sendRedirect("employeeError.jsp");
            }
        }
    }
}



//public class AddEmployeeServlet extends HttpServlet {
//    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//
//        // Retrieve the step from the form
//        String step = request.getParameter("step");
//
//        // Handle Step 1: Basics
//        if ("1".equals(step)) {
//            String staffFullname = request.getParameter("staffFullname");
//            String staffName = request.getParameter("staffName");
//            String staffJoinedDateStr = request.getParameter("staffJoinedDate");
//            String staffGender = request.getParameter("staffGender");
//            String staffPosition = request.getParameter("staffPosition");
//
//            Date staffJoinedDate = parseDate(staffJoinedDateStr);
//            session.setAttribute("staffFullname", staffFullname);
//            session.setAttribute("staffName", staffName);
//            session.setAttribute("staffJoinedDate", staffJoinedDate);
//            session.setAttribute("staffGender", staffGender);
//            session.setAttribute("staffPosition", staffPosition);
//            response.sendRedirect("foAddEmployee2.jsp");
//        }
//
//        // Handle Step 3: Personal Info
//        if ("3".equals(step)) {
//            String staffDOBStr = request.getParameter("staffDOB");
//            String staffAddress = request.getParameter("staffAddress");
//            String staffPhoneNo = request.getParameter("staffPhoneNo");
//            String staffEmail = request.getParameter("staffEmail");
//            String staffMaritalStatus = request.getParameter("staffMaritalStatus");
//            String staffEmpType = request.getParameter("staffEmpType");
//
//            Date staffDOB = parseDate(staffDOBStr);
//            session.setAttribute("staffDOB", staffDOB);
//            session.setAttribute("staffAddress", staffAddress);
//            session.setAttribute("staffPhoneNo", staffPhoneNo);
//            session.setAttribute("staffEmail", staffEmail);
//            session.setAttribute("staffMaritalStatus", staffMaritalStatus);
//            session.setAttribute("staffEmpType", staffEmpType);
//            response.sendRedirect("foAddEmployee4.jsp");
//        }
//
//        // Handle Step 4: Payment Info
//        if ("4".equals(step)) {
//            String staffBank = request.getParameter("staffBank");
//            String staffAccNo = request.getParameter("staffAccNo");
//            session.setAttribute("staffBank", staffBank);
//            session.setAttribute("staffAccNo", staffAccNo);
//
//            // Save all data to the database
//            try {
//                saveToDatabase(session);
//                // Display a success message on the browser
//                response.setContentType("text/html;charset=UTF-8");
//                response.getWriter().write("<!DOCTYPE html>");
//                response.getWriter().write("<html>");
//                response.getWriter().write("<head>");
//                response.getWriter().write("<title>Success</title>");
//                response.getWriter().write("</head>");
//                response.getWriter().write("<body>");
//                response.getWriter().write("<h1 style='color: green; text-align: center;'>New Employee is added successfully!</h1>");
//                response.getWriter().write("<div style='text-align: center;'><a href='foDashboard.jsp'>Back to Dashboard</a></div>");
//                response.getWriter().write("</body>");
//                response.getWriter().write("</html>");
//            } catch (SQLException e) {
//                throw new ServletException("Database error", e);
//            }
//        }
//    }
//
//    private Date parseDate(String dateStr) {
//        if (dateStr == null || dateStr.isEmpty()) {
//            return null;
//        }
//        try {
//            return DATE_FORMAT.parse(dateStr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    private void saveToDatabase(HttpSession session) throws SQLException {
//        Staff staff = new Staff();
//
//        // Retrieve session attributes and set default values if necessary
//        staff.setStaffFullname((String) session.getAttribute("staffFullname"));
//        staff.setStaffName((String) session.getAttribute("staffName"));
//        staff.setStaffJoinedDate((Date) session.getAttribute("staffJoinedDate"));
//        staff.setStaffGender((String) session.getAttribute("staffGender"));
//        staff.setStaffPosition((String) session.getAttribute("staffPosition"));
//
//        // staffPosition - Ensure it's not null or empty
//        String staffPosition = (String) session.getAttribute("staffPosition");
//        if (staffPosition == null || staffPosition.isEmpty()) {
//            staffPosition = "UNKNOWN"; // Default position if not provided
//        }
//        staff.setStaffPosition(staffPosition);
//
//        staff.setStaffAddress((String) session.getAttribute("staffAddress"));
//        staff.setStaffPhoneno((String) session.getAttribute("staffPhoneNo"));
//        staff.setStaffEmail((String) session.getAttribute("staffEmail"));
//        staff.setStaffMaritalStatus((String) session.getAttribute("staffMaritalStatus"));
//        staff.setStaffEmpType((String) session.getAttribute("staffEmpType"));
//        staff.setStaffBank((String) session.getAttribute("staffBank"));
//        staff.setStaffAccNo((String) session.getAttribute("staffAccNo"));
//        staff.setStaffRole((Role) session.getAttribute("StaffRole"));
//        staff.setStaffPassword((String) session.getAttribute("StaffPassword"));
//        staff.setStaffDOB((Date) session.getAttribute("staffDOB"));
//
//        // staffDOB - Ensure it's not null
//        Date staffDOB = (Date) session.getAttribute("staffDOB");
//        if (staffDOB == null) {
//            staffDOB = new Date();  // Default to current date if not provided
//        }
//        staff.setStaffDOB(staffDOB);
//
//        // staffPassword - Ensure it's not null or empty
//        String staffPassword = (String) session.getAttribute("staffPassword");
//        if (staffPassword == null || staffPassword.isEmpty()) {
//            staffPassword = "defaultPassword"; // Default password if not provided
//        }
//        staff.setStaffPassword(staffPassword);
//
//        // staffRole - Ensure it's not null
//        Role staffRole = (Role) session.getAttribute("staffRole");
//        if (staffRole == null) {
//            staffRole = Role.GENERAL_STAFF; // Default role if not provided
//        }
//        staff.setStaffRole(staffRole);
//
//        // Create the DAO object and save the staff to the database
//        StaffDAO staffDAO = new StaffDAO();
//        int staffID = staffDAO.saveStaff(staff);
//        session.setAttribute("staffID", staffID);
//    }
//}
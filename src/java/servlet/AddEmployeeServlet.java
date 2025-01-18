/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.SalaryDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Role;
import model.Salary;
import model.Staff;

public class AddEmployeeServlet extends HttpServlet {

    private StaffDAO staffDAO = new StaffDAO();  // StaffDAO for database interaction

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
            session.setAttribute("salaryOvtRate", salaryOvtRate); 

            // Redirect to next form step
            response.sendRedirect("foAddEmployee3.jsp");
        } else if ("3".equals(step)) {
            // Step 3: Capture role, password, etc., and save it to the session
            String staffRoleValue = request.getParameter("staffRole");
            String staffPassword = request.getParameter("staffPassword");
            String staffDOB = request.getParameter("staffDOB");
            String staffAddress = request.getParameter("staffAddress");
            String staffPhoneNo = request.getParameter("staffPhoneNo");
            String staffEmail = request.getParameter("staffEmail");
            String staffMaritalStatus = request.getParameter("staffMaritalStatus");
            String staffEmpType = request.getParameter("staffEmpType");

            // Save data to session
            session.setAttribute("staffRole", staffRoleValue);
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
        
        int roleValue = Integer.parseInt((String) session.getAttribute("staffRole")); // Retrieve numeric value
        Role staffRole = Role.fromValue(roleValue); // Convert numeric value to Role enum

        // Convert the date strings to Date objects
        String staffJoinedDate = (String) session.getAttribute("staffJoinedDate");
        String staffDOB = (String) session.getAttribute("staffDOB");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date joinedDate = dateFormat.parse(staffJoinedDate); // Parse staffJoinedDate
        Date dob = dateFormat.parse(staffDOB); // Parse staffDOB

        // Create the new employee (Staff object)
        Staff newEmployee = new Staff(
                0,  // Assuming staffID is auto-generated by the database
                (String) session.getAttribute("staffPassword"),
                staffRole,  // Use Role enum here
                //Role.valueOf((String) session.getAttribute("staffRole")),  // Ensure Role enum matches
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
            StaffDAO staffDAO = new StaffDAO();
            int staffID = staffDAO.saveStaff(newEmployee); // Assuming `saveStaff` returns the generated staffID

            if (staffID > 0) {
                // Retrieve salary details from the session
                double salaryBasic = Double.parseDouble((String) session.getAttribute("salaryBasic"));
                double salaryDeduction = Double.parseDouble((String) session.getAttribute("salaryDeduction"));
                double salaryOvtRate = Double.parseDouble((String) session.getAttribute("salaryOvtRate"));

                // Create the new salary record (Salary object)
                Salary newSalary = new Salary(
                        0,  // salaryID, assumed to be auto-generated by the database
                        staffID,  // Use the generated staffID
                        salaryBasic,
                        salaryDeduction,
                        salaryOvtRate
                );

                // Save the salary details to the database
                SalaryDAO salaryDAO = new SalaryDAO();
                boolean salarySaved = salaryDAO.addSalary(newSalary);

                if (salarySaved) {
                    // Success, redirect to the success page
                    session.setAttribute("message", "Employee and salary added successfully!");
                    session.invalidate();  // Clean up session
                    response.sendRedirect("employeeSuccess.jsp");
                } else {
                    // Failure in saving salary
                    session.setAttribute("message", "Error: Salary could not be added.");
                    response.sendRedirect("employeeError.jsp");
                }
            } else {
                // Failure in saving staff
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
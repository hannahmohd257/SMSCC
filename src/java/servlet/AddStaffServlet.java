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
import model.Salary;
import model.Staff;

public class AddStaffServlet extends HttpServlet {

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
            String staffRole = request.getParameter("staffRole");
            String staffPassword = request.getParameter("staffPassword");
            String staffDOB = request.getParameter("staffDOB");
            String staffAddress = request.getParameter("staffAddress");
            String staffPhoneno = request.getParameter("staffPhoneno");
            String staffEmail = request.getParameter("staffEmail");
            String staffMaritalStatus = request.getParameter("staffMaritalStatus");
            String staffEmpType = request.getParameter("staffEmpType");

            // Convert staffRole to integer
//            int staffRole = Integer.parseInt(staffRoleValue);  // Convert role to numeric value (1, 2, 3)

            // Save data to session
            session.setAttribute("staffRole", staffRole);
            session.setAttribute("staffPassword", staffPassword);
            session.setAttribute("staffDOB", staffDOB);
            session.setAttribute("staffAddress", staffAddress);
            session.setAttribute("staffPhoneno", staffPhoneno);
            session.setAttribute("staffEmail", staffEmail);
            session.setAttribute("staffMaritalStatus", staffMaritalStatus);
            session.setAttribute("staffEmpType", staffEmpType);
            // Redirect to next form step
            response.sendRedirect("foAddEmployee4.jsp");
        } if ("4".equals(step)) {
            String staffBank = request.getParameter("staffBank");
            String staffAccNo = request.getParameter("staffAccNo");
            session.setAttribute("staffBank", staffBank);
            session.setAttribute("staffAccNo", staffAccNo);

            try {

                // Parse dates
                String staffJoinedDate = (String) session.getAttribute("staffJoinedDate");
                String staffDOB = (String) session.getAttribute("staffDOB");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date joinedDate = dateFormat.parse(staffJoinedDate);
                Date dob = dateFormat.parse(staffDOB);

                // Create Staff object
                Staff newEmployee = new Staff(
                    0,  // staffID
                    (String) session.getAttribute("staffPassword"),
                    (String) session.getAttribute("staffRole"),
                    (String) session.getAttribute("staffName"),
                    (String) session.getAttribute("staffFullname"),
                    (String) session.getAttribute("staffEmail"),
                    joinedDate, // staffJoinedDate
                    (String) session.getAttribute("staffPosition"),
                    (String) session.getAttribute("staffPhoneno"),
                    dob, // staffDOB
                    (String) session.getAttribute("staffAddress"),
                    (String) session.getAttribute("staffGender"),
                    (String) session.getAttribute("staffMaritalStatus"),
                    (String) session.getAttribute("staffEmpType"),
                    (String) session.getAttribute("staffBank"), // staffBank
                    (String) session.getAttribute("staffAccNo")  // staffAccNo
                );

                // Save Staff object
                StaffDAO staffDAO = new StaffDAO();
                int staffID = staffDAO.saveStaff(newEmployee);  // Save and get staffID

                if (staffID > 0) {
                    double salaryBasic = Double.parseDouble((String) session.getAttribute("salaryBasic"));
                    double salaryDeduction = Double.parseDouble((String) session.getAttribute("salaryDeduction"));
                    double salaryOvtRate = Double.parseDouble((String) session.getAttribute("salaryOvtRate"));

                    // Create Salary object
                    Salary newSalary = new Salary(0, staffID, salaryBasic, salaryDeduction, salaryOvtRate);

                    // Save Salary object
                    SalaryDAO salaryDAO = new SalaryDAO();
                    boolean salarySaved = salaryDAO.addSalary(newSalary);

                    if (salarySaved) {
                        session.setAttribute("message", "Employee and salary added successfully!");
                        session.invalidate();
                        response.sendRedirect("staffAddSuccess.jsp");
                    } else {
                        session.setAttribute("message", "Error: Salary could not be added.");
                        response.sendRedirect("employeeError.jsp");
                    }
                } else {
                    session.setAttribute("message", "Error: Employee could not be added.");
                    response.sendRedirect("employeeError.jsp");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                session.setAttribute("message", "Invalid date format: " + e.getMessage());
                response.sendRedirect("employeeError.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("message", "Database error: " + e.getMessage());
                response.sendRedirect("employeeError.jsp");
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported. Please use POST.");
    }
}

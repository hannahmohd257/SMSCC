/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import model.Role;
import model.Staff;
import dao.StaffDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private StaffDAO staffDAO = new StaffDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int staffID = Integer.parseInt(request.getParameter("staffID"));
        String staffPassword = request.getParameter("staffPassword");
        String staffRoleValue = request.getParameter("staffRole");

        try {
            // Parse the role value and validate credentials
            Role staffRole = Role.fromValue(Integer.parseInt(staffRoleValue)); // Convert role to enum
            boolean isValid = staffDAO.validateStaffCredentials(staffID, staffPassword, staffRole);

            HttpSession session = request.getSession();
            session.invalidate(); // Clear old session
            session = request.getSession(true); // Create new session
            session.setAttribute("staffRole", "FINANCE_OFFICER"); // Set necessary attributes

            if (isValid) {
                // Retrieve the staff details from the database
                //Staff staff = staffDAO.getStaffByID(staffID);
                Staff staff = staffDAO.getStaffByID(staffID);

                if (staff != null) {
                    // Save staff information in the session
                    session.setAttribute("staffName", staff.getStaffName()); // Retrieved staffName
                    session.setAttribute("staffFullname", staff.getStaffFullname()); // Retrieved staffFullname
                    session.setAttribute("staffRole", staff.getStaffRole());

                    // Redirect to the appropriate dashboard based on the role
                    switch (staffRole) {
                        case GENERAL_STAFF:
                            response.sendRedirect("staffDashboard.jsp");
                            break;
                        case FINANCE_OFFICER:
                            response.sendRedirect("foDashboard.jsp");
                            break;
                        case MANAGER:
                            response.sendRedirect("managerDashboard.jsp");
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid role.");
                    }
                } else {
                    request.setAttribute("errorMessage", "Staff details not found.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                // Invalid login
                request.setAttribute("errorMessage", "Invalid login credentials or role selected.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

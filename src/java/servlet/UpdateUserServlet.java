/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.FODAO;
import dao.HRDAO;
import dao.ManagerDAO;
import dao.StaffDAO;
import dao.UserDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Staff;
import model.User; // Make sure this import exists

public class UpdateUserServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO(); // For all users
    private StaffDAO staffDAO = new StaffDAO(); // Only for staff
    private ManagerDAO managerDAO = new ManagerDAO();
    private HRDAO hrDAO = new HRDAO();
    private FODAO financeDAO = new FODAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");

        if (userID == null || userID.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid userID.");
            return;
        }

        try {
            User user = userDAO.getUserById(userID);

            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
                return;
            }

            request.setAttribute("user", user);

            // If the user is staff, fetch staff details too
            if ("Staff".equalsIgnoreCase(user.getRole())) {
                Staff staff = staffDAO.getUserById(userID);
                request.setAttribute("staff", staff);
            }

            request.getRequestDispatcher("hrEditUserOverview.jsp").forward(request, response); // Updated JSP name

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving user details.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action"); // Can be "update" or "delete"
        String userID = request.getParameter("userID");
        String role = request.getParameter("role");

        try {
            if ("delete".equalsIgnoreCase(action)) {
                // Delete from the role-specific table first
                switch (role.toLowerCase()) {
                    case "staff":
                        staffDAO.deleteStaff(userID);
                        break;
                    case "manager":
                        managerDAO.deleteManager(userID);
                        break;
                    case "human resource":
                        hrDAO.deleteHR(userID);
                        break;
                    case "finance officer":
                        financeDAO.deleteFO(userID);
                        break;
                    default:
                        System.out.println("No role-specific table deletion needed for role: " + role);
                }

                // Delete from User table
                userDAO.deleteUser(userID);

                // Redirect back to list
                response.sendRedirect("UserListServlet?role=" + role);
                return;
            }


            // ========== UPDATE logic ==========

            // User table fields
            String fullname = request.getParameter("fullname");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
//            String password = request.getParameter("password");

            // Build User object
            User user = new User();
            user.setUserID(userID);
            user.setUsername(username);
            user.setFullname(fullname);
//            user.setPassword(password);
            user.setEmail(email);
            user.setRole(role);

            // Update User record
            userDAO.updateUser(user);

            // If user is a staff, also update staff-specific data
            if ("Staff".equalsIgnoreCase(role)) {
                String staffPosition = request.getParameter("staffPosition");
                String staffAddress = request.getParameter("staffAddress");
                String staffPhoneno = request.getParameter("staffPhoneno");
                String staffMaritalStatus = request.getParameter("staffMaritalStatus");
                String staffEmpType = request.getParameter("staffEmpType");
                String staffBank = request.getParameter("staffBank");
                String staffAccNo = request.getParameter("staffAccNo");
                String staffGender = request.getParameter("staffGender");
                String basicSalaryStr = request.getParameter("basicSalary");
                if (basicSalaryStr == null || basicSalaryStr.isEmpty()) {
                    throw new IllegalArgumentException("Basic Salary is missing or invalid");
                }
                double basicSalary = Double.parseDouble(basicSalaryStr);


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date staffDOB = new java.sql.Date(dateFormat.parse(request.getParameter("staffDOB")).getTime());
                Date staffJoinedDate = new java.sql.Date(dateFormat.parse(request.getParameter("staffJoinedDate")).getTime());

                Staff staff = new Staff();
                staff.setUserID(userID);
                staff.setStaffJoinedDate(staffJoinedDate);
                staff.setStaffGender(staffGender);
                staff.setStaffPosition(staffPosition);
                staff.setStaffAddress(staffAddress);
                staff.setStaffPhoneno(staffPhoneno);
                staff.setStaffMaritalStatus(staffMaritalStatus);
                staff.setStaffEmpType(staffEmpType);
                staff.setStaffBank(staffBank);
                staff.setStaffAccNo(staffAccNo);
                staff.setStaffDOB(staffDOB);
                staff.setBasicSalary(basicSalary);

                staffDAO.updateStaff(staff);
            }

            response.sendRedirect("UserDetailsServlet?userID=" + userID + "&viewType=overview");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data.");
        }
    }
}

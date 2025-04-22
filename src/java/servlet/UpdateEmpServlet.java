/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.StaffDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Staff;

public class UpdateEmpServlet extends HttpServlet {
    private StaffDAO staffDAO = new StaffDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parse the staffID from the request
        int staffID = Integer.parseInt(request.getParameter("staffID"));
        
        // Create an instance of StaffDAO
        StaffDAO staffDAO = new StaffDAO();

        // Fetch staff details from the database
        Staff staff = staffDAO.getStaffByID(staffID);

        // Set staff details as a request attribute
        request.setAttribute("staff", staff);

        // Forward the request to the editStaff.jsp page
        request.getRequestDispatcher("foEditStaffOverview.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve parameters from the form
            int staffID = Integer.parseInt(request.getParameter("staffID"));
            String staffFullname = request.getParameter("staffFullname");
            String staffName = request.getParameter("staffName");
            String staffPosition = request.getParameter("staffPosition");
            String staffAddress = request.getParameter("staffAddress");
            String staffPhoneno = request.getParameter("staffPhoneno");
            String staffEmail = request.getParameter("staffEmail");
            String staffMaritalStatus = request.getParameter("staffMaritalStatus");
            String staffEmpType = request.getParameter("staffEmpType");
            String staffBank = request.getParameter("staffBank");
            String staffAccNo = request.getParameter("staffAccNo");
            String staffPassword = request.getParameter("staffPassword");
            String staffRole = request.getParameter("staffRole");
            String staffGender = request.getParameter("staffGender");

            // Parse dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date staffDOB = new java.sql.Date(dateFormat.parse(request.getParameter("staffDOB")).getTime());
            Date staffJoinedDate = new java.sql.Date(dateFormat.parse(request.getParameter("staffJoinedDate")).getTime());

            // Create a Staff object
            Staff staff = new Staff(staffID, staffPassword, staffRole, staffName, staffFullname, staffEmail, 
                        staffJoinedDate, staffPosition, staffPhoneno, staffDOB, staffAddress, 
                        staffGender, staffMaritalStatus, staffEmpType, staffBank, staffAccNo);


            // Update staff in the database
            boolean success = staffDAO.updateStaff(staff);

            if (success) {
                // Redirect to the overview page
                response.sendRedirect("StaffDetailsServlet?staffID=" + staffID + "&viewType=overview");
            } else {
                // Handle failure
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update staff details.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data.");
        }
    }
}

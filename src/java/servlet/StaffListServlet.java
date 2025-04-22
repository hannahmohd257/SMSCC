/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.StaffDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Staff;

public class StaffListServlet extends HttpServlet {

    private StaffDAO staffDAO = new StaffDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String staffIDParam = request.getParameter("staffID");

        if ("delete".equals(action) && staffIDParam != null) {
            try {
                int staffID = Integer.parseInt(staffIDParam);

                // Call DAO to delete employee
                StaffDAO staffDAO = new StaffDAO();
                boolean isDeleted = staffDAO.deleteStaff(staffID);

                if (isDeleted) {
                    request.setAttribute("message", "Employee deleted successfully.");
                } else {
                    request.setAttribute("error", "Failed to delete employee.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid staff ID.");
            }
        }

        // Fetch updated employee list
        StaffDAO staffDAO = new StaffDAO();
        List<Staff> staffList = staffDAO.getAllEmployees();
        request.setAttribute("staffList", staffList);

        // Forward to the JSP page
        request.getRequestDispatcher("foStaff.jsp").forward(request, response);
    }

}

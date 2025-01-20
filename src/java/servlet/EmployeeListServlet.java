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
import javax.servlet.http.HttpSession;
import model.Staff;

public class EmployeeListServlet extends HttpServlet {

    private StaffDAO staffDAO = new StaffDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Staff> employeeList = staffDAO.getAllEmployees();
        request.setAttribute("employeeList", employeeList);

        request.getRequestDispatcher("foEmployees.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String staffID = request.getParameter("staffID");
            if (staffID != null && !staffID.isEmpty()) {
                try {
                    int staffIdInt = Integer.parseInt(staffID);
                    boolean isDeleted = staffDAO.deleteStaff(staffIdInt);

                    if (isDeleted) {
                        response.sendRedirect("EmployeeListServlet?success=true");
                    } else {
                        response.sendRedirect("EmployeeListServlet?error=deleteFailed");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("EmployeeListServlet?error=invalidIDFormat");
                }
            } else {
                response.sendRedirect("EmployeeListServlet?error=invalidID");
            }
        }
    }
}

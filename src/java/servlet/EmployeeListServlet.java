/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.StaffDAO;
import model.Staff;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class EmployeeListServlet extends HttpServlet {

    private StaffDAO staffDAO = new StaffDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

         //Ensure the user is logged in and is a Finance Officer
        if (session == null || session.getAttribute("staffRole") == null || 
            !session.getAttribute("staffRole").toString().equals("Finance Officer")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Retrieve the list of employees
        List<Staff> employeeList = staffDAO.getAllEmployees();

        // Pass the employee list to the JSP
        request.setAttribute("employeeList", employeeList);
        request.getRequestDispatcher("foEmployees.jsp").forward(request, response);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddEmployeeServlet extends HttpServlet {
    private HashMap<String, String> employeeData = new HashMap<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String step = request.getParameter("step");

        switch (step) {
            case "1":
                // Store basic info
                employeeData.put("staffName", request.getParameter("staffName"));
                employeeData.put("joinedDate", request.getParameter("joinedDate"));
                employeeData.put("gender", request.getParameter("gender"));
                employeeData.put("position", request.getParameter("position"));
                request.getRequestDispatcher("foAddEmployee2.jsp").forward(request, response);
                break;

            case "2":
                // Store salary details
                employeeData.put("basicSalary", request.getParameter("basicSalary"));
                employeeData.put("deductions", request.getParameter("deductions"));
                employeeData.put("overtimeRate", request.getParameter("overtimeRate"));
                request.getRequestDispatcher("foAddEmployee3.jsp").forward(request, response);
                break;

            case "3":
                // Store personal info
                employeeData.put("dateOfBirth", request.getParameter("dateOfBirth"));
                employeeData.put("address", request.getParameter("address"));
                employeeData.put("contactNumber", request.getParameter("contactNumber"));
                employeeData.put("email", request.getParameter("email"));
                employeeData.put("maritalStatus", request.getParameter("maritalStatus"));
                employeeData.put("employmentType", request.getParameter("employmentType"));
                request.getRequestDispatcher("foAddEmployee4.jsp").forward(request, response);
                break;

            case "4":
                // Store payment info
                employeeData.put("bankName", request.getParameter("bankName"));
                employeeData.put("bankAccountNumber", request.getParameter("bankAccountNumber"));

                // Process or save data (simulate by printing)
                response.getWriter().println("Employee Data Submitted Successfully!");
                for (String key : employeeData.keySet()) {
                    response.getWriter().println(key + ": " + employeeData.get(key));
                }
                break;

            default:
                response.sendRedirect("foAddEmployee1.jsp");
        }
    }
}

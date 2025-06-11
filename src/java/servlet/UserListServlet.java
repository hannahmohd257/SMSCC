/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.User;

public class UserListServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getParameter("role");

        if (role != null) {
            try {
                // Fetch the users based on the selected role
                List<User> userList = userDAO.getUserByRole(role);

                // Set the list of users as a request attribute
                request.setAttribute("userList", userList);

                // Forward the request to the JSP page for rendering
                request.getRequestDispatcher("hrUsers.jsp").forward(request, response);

            } catch (Exception e) {
                // Handle errors (e.g., database issues)
                request.setAttribute("error", "Error fetching user list: " + e.getMessage());
                request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Role parameter is missing.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }
}

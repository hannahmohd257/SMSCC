/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.UserDAO;
import dao.ManagerDAO;
import dao.HRDAO;
import dao.FODAO;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.DBConnection;

public class AddUserByRoleServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private ManagerDAO managerDAO = new ManagerDAO();
    private HRDAO hrDAO = new HRDAO();
    private FODAO foDAO = new FODAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getParameter("role"); // Expected: Manager, Human Resource, Finance Officer
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String position = request.getParameter("position");

        role = role != null ? role.trim() : "";
        String normalizedRole = role.toLowerCase();

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            // Insert into user table
            String userID = userDAO.insertUser(role, fullname, username, password, email, conn);
            if (userID == null) {
                request.setAttribute("message", "Error: Failed to insert user.");
                request.getRequestDispatcher("roleError.jsp").forward(request, response);
                return;
            }

            boolean roleSaved = false;

            switch (normalizedRole) {
                case "manager":
                    roleSaved = managerDAO.saveManager(userID, position, conn);
                    break;
                case "finance officer":
                    roleSaved = foDAO.saveFO(userID, position, conn);
                    break;
                case "human resource":
                    roleSaved = hrDAO.saveHR(userID, position, conn);
                    break;
                default:
                    request.setAttribute("message", "Invalid role specified.");
                    request.getRequestDispatcher("roleError.jsp").forward(request, response);
                    return;
            }

            if (roleSaved) {
                conn.commit(); // All good
                request.setAttribute("message", "User added successfully!");
                request.getRequestDispatcher("userAddSuccess.jsp").forward(request, response);
            } else {
                conn.rollback(); // Rollback if role insert fails
                request.setAttribute("message", "Error: Role-specific data could not be saved.");
                request.getRequestDispatcher("roleError.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "System error: " + e.getMessage());
            request.getRequestDispatcher("roleError.jsp").forward(request, response);
        }
    }
}

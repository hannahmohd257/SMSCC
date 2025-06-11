
package servlet;

import dao.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import model.User;

public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get parameters
        String userID = request.getParameter("userID").trim();
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        

        // Debug logging
        System.out.printf("Login attempt - UserID: %s, Role: %s%n", userID, role);

        // Validate input
        if (userID.isEmpty() || password.isEmpty() || role == null) {
            sendError(request, response, "All fields are required");
            return;
        }

        try {
            // Authenticate user
            User user = userDAO.validateAndGetUser(userID, password, role);

            if (user != null) {
                handleSuccessfulLogin(request, response, user);
            } else {
                sendError(request, response, "Invalid credentials or role mismatch");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendError(request, response, "System error during login");
        }
    }

    private void handleSuccessfulLogin(HttpServletRequest request, 
                                     HttpServletResponse response,
                                     User user) throws IOException, ServletException {
        // Create new session (invalidate old one if exists)
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        
        HttpSession newSession = request.getSession(true);
        
        // Set session attributes
        newSession.setAttribute("user", user);  // Store entire user object
        newSession.setAttribute("userID", user.getUserID());
        newSession.setAttribute("username", user.getUsername());
        newSession.setAttribute("role", user.getRole());
        newSession.setAttribute("fullname", user.getFullname());
        newSession.setAttribute("email", user.getEmail());
        
        
        // Set session timeout (30 minutes)
        newSession.setMaxInactiveInterval(30 * 60);
        
        // Debug logging
        System.out.printf("Login successful - UserID: %s, Role: %s, SessionID: %s%n",
                user.getUserID(), user.getRole(), newSession.getId());

        // Redirect based on role
        String redirectPath = getDashboardPath(user.getRole());
        if (redirectPath != null) {
            System.out.println("Redirecting to: " + redirectPath);
            response.sendRedirect(request.getContextPath() + redirectPath);
        } else {
            sendError(request, response, "Invalid user role");
        }
    }

    private String getDashboardPath(String role) {
        switch (role) {
            case "Staff": return "/staffDashboard.jsp";
            case "Human Resource": return "/hrDashboard.jsp";
            case "Finance Officer": return "/foDashboard.jsp";
            case "Manager": return "/managerDashboard.jsp";
            default: return null;
        }
    }

    private void sendError(HttpServletRequest request,
                         HttpServletResponse response,
                         String message) throws ServletException, IOException {
        System.out.println("Login failed: " + message);
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
package servlet;

import dao.StaffDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Staff;
import model.User;

public class UserDetailsServlet extends HttpServlet {

    private UserDAO userDAO;
    private StaffDAO staffDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        staffDAO = new StaffDAO();  // Still used for staff-specific info
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("userID");
        String viewType = request.getParameter("viewType");

        if (userId == null || viewType == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters.");
            return;
        }

        User user = userDAO.getUserById(userId);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            return;
        }

        request.setAttribute("user", user);

        // Only load staff details if role is Staff
        if ("Staff".equalsIgnoreCase(user.getRole())) {
            Staff staff = staffDAO.getStaffByUserID(userId);
            request.setAttribute("staff", staff);
        }

        // Forward to the correct JSP based on viewType
        switch (viewType) {
            case "overview":
                request.getRequestDispatcher("hrUserOverview.jsp").forward(request, response);
                break;
            case "salary":
                request.getRequestDispatcher("hrUserSalary.jsp").forward(request, response);
                break;
            case "payslip":
                request.getRequestDispatcher("hrUserPayslip.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid view type.");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.StaffDAO;
import dao.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Staff;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

public class AddStaffServlet extends HttpServlet {

    private StaffDAO staffDAO = new StaffDAO();
    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String step = request.getParameter("step");
        HttpSession session = request.getSession();

        try {
            if ("1".equals(step)) {
                processStep1(request, response, session);
            } else if ("2".equals(step)) {
                processStep2(request, response, session);
            } else if ("3".equals(step)) {
                processStep3(request, response, session);
            } else {
                session.setAttribute("error", "Invalid form step");
                response.sendRedirect("hrAddStaff1.jsp");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Error: " + e.getMessage());
            response.sendRedirect(getErrorRedirectPage(step));
            e.printStackTrace();
        }
    }

    private void processStep1(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, SQLException {
        // Validate required fields
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String staffJoinedDate = request.getParameter("staffJoinedDate");
        String staffGender = request.getParameter("staffGender");
        String staffPosition = request.getParameter("staffPosition");
        String basicSalaryStr = request.getParameter("basicSalary");

        // Validation checks
        if (isEmpty(fullname) || isEmpty(username) || isEmpty(staffJoinedDate) || 
            isEmpty(staffGender) || isEmpty(staffPosition) || isEmpty(basicSalaryStr)) {
            session.setAttribute("error", "All fields are required");
            response.sendRedirect("hrAddStaff1.jsp");
            return;
        }

        try {
            double basicSalary = Double.parseDouble(basicSalaryStr);
            if (basicSalary <= 0) {
                session.setAttribute("error", "Basic salary must be positive");
                response.sendRedirect("hrAddStaff1.jsp");
                return;
            }

            // Check if username already exists
            if (userDAO.usernameExists(username)) {
                session.setAttribute("error", "Username already exists");
                response.sendRedirect("hrAddStaff1.jsp");
                return;
            }

            // Save valid data to session
            session.setAttribute("fullname", fullname);
            session.setAttribute("username", username);
            session.setAttribute("staffJoinedDate", staffJoinedDate);
            session.setAttribute("staffGender", staffGender);
            session.setAttribute("staffPosition", staffPosition);
            session.setAttribute("basicSalary", basicSalary);

            response.sendRedirect("hrAddStaff2.jsp");
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid salary format");
            response.sendRedirect("hrAddStaff1.jsp");
        }
    }

    private void processStep2(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        // Get input fields
        String password = request.getParameter("password");
        String staffDOB = request.getParameter("staffDOB");
        String staffAddress = request.getParameter("staffAddress");
        String staffPhoneno = request.getParameter("staffPhoneno");
        String email = request.getParameter("email");
        String staffMaritalStatus = request.getParameter("staffMaritalStatus");
        String staffEmpType = request.getParameter("staffEmpType");

        // Validation checks
        if (isEmpty(password) || isEmpty(staffDOB) || isEmpty(staffAddress) || 
            isEmpty(staffPhoneno) || isEmpty(email) || isEmpty(staffMaritalStatus) || 
            isEmpty(staffEmpType)) {
            session.setAttribute("error", "All fields are required");
            response.sendRedirect("hrAddStaff2.jsp");
            return;
        }

        if (!isValidEmail(email)) {
            session.setAttribute("error", "Invalid email format");
            response.sendRedirect("hrAddStaff2.jsp");
            return;
        }

        if (!staffPhoneno.matches("\\d{10,15}")) {
            session.setAttribute("error", "Phone number should be 10-15 digits");
            response.sendRedirect("hrAddStaff2.jsp");
            return;
        }

        if (password.length() < 5) {
            session.setAttribute("error", "Password must be at least 5 characters");
            response.sendRedirect("hrAddStaff2.jsp");
            return;
        }

        // 🔐 Hash password before storing in session
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Store valid data in session
        session.setAttribute("password", hashedPassword); // store hashed version
        session.setAttribute("staffDOB", staffDOB);
        session.setAttribute("staffAddress", staffAddress);
        session.setAttribute("staffPhoneno", staffPhoneno);
        session.setAttribute("email", email);
        session.setAttribute("staffMaritalStatus", staffMaritalStatus);
        session.setAttribute("staffEmpType", staffEmpType);

        response.sendRedirect("hrAddStaff3.jsp");
    }

    private void processStep3(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ParseException, SQLException {
        String staffBank = request.getParameter("staffBank");
        String staffAccNo = request.getParameter("staffAccNo");
        if (staffAccNo != null) {
            staffAccNo = staffAccNo.trim();
        } else {
            staffAccNo = "";
        }

        if ("Other".equals(staffBank)) {
            String otherBank = request.getParameter("otherBankName");
            if (!isEmpty(otherBank)) {
                staffBank = otherBank.trim();
            }
        }

        if (isEmpty(staffBank) || isEmpty(staffAccNo)) {
            session.setAttribute("error", "Bank name and account number are required");
            response.sendRedirect("hrAddStaff3.jsp");
            return;
        }

        if (!staffAccNo.matches("^\\d{8,20}$")) {
            session.setAttribute("error", "Account number must contain 8-20 digits only");
            response.sendRedirect("hrAddStaff3.jsp");
            return;
        }
        // Validate all required session data
        String[] requiredSessionAttributes = {
            "fullname", "username", "password", "email", "staffJoinedDate",
            "staffGender", "staffPosition", "basicSalary", "staffDOB",
            "staffAddress", "staffPhoneno", "staffMaritalStatus", "staffEmpType"
        };

        for (String attr : requiredSessionAttributes) {
            if (session.getAttribute(attr) == null) {
                session.setAttribute("error", "Missing information. Please start over.");
                response.sendRedirect("hrAddStaff1.jsp");
                return;
            }
        }

        // Parse date fields
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date joinedDate = dateFormat.parse((String) session.getAttribute("staffJoinedDate"));
        Date dob = dateFormat.parse((String) session.getAttribute("staffDOB"));

        // Get hashed password from session (already hashed in step 2)
        String hashedPassword = (String) session.getAttribute("password");

        // Create and populate User object
        User newUser = new User();
        newUser.setFullname((String) session.getAttribute("fullname"));
        newUser.setUsername((String) session.getAttribute("username"));
        newUser.setPassword(hashedPassword);
        newUser.setEmail((String) session.getAttribute("email"));
        newUser.setRole("Staff");

        // Create and populate Staff object
        Staff newStaff = new Staff();
        newStaff.setStaffJoinedDate(joinedDate);
        newStaff.setStaffGender((String) session.getAttribute("staffGender"));
        newStaff.setStaffPosition((String) session.getAttribute("staffPosition"));
        newStaff.setStaffAddress((String) session.getAttribute("staffAddress"));
        newStaff.setStaffPhoneno((String) session.getAttribute("staffPhoneno"));
        newStaff.setStaffMaritalStatus((String) session.getAttribute("staffMaritalStatus"));
        newStaff.setStaffEmpType((String) session.getAttribute("staffEmpType"));
        newStaff.setStaffBank(staffBank);
        newStaff.setStaffAccNo(staffAccNo);
        newStaff.setStaffDOB(dob);
        newStaff.setBasicSalary((Double) session.getAttribute("basicSalary"));

        // Save user and staff
        String userID = staffDAO.saveStaff(newUser, newStaff);

        if (userID != null && !userID.isEmpty()) {
            session.setAttribute("message", "Staff added successfully!");
            session.removeAttribute("error");
            for (String attr : requiredSessionAttributes) {
                session.removeAttribute(attr);
            }
            session.removeAttribute("staffBank");
            session.removeAttribute("staffAccNo");
            response.sendRedirect("userAddSuccess.jsp");
        } else {
            session.setAttribute("error", "Failed to save staff to database");
            response.sendRedirect("hrAddStaff3.jsp");
        }
    }

    // Helper methods
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private String getErrorRedirectPage(String step) {
        switch (step) {
            case "1": return "hrAddStaff1.jsp";
            case "2": return "hrAddStaff2.jsp";
            case "3": return "hrAddStaff3.jsp";
            default: return "hrAddStaff1.jsp";
        }
    }
}

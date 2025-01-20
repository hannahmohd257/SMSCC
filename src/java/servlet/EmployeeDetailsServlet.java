package servlet;



import dao.SalaryDAO;
import dao.StaffDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Salary;
import model.Staff;

public class EmployeeDetailsServlet extends HttpServlet {
    private StaffDAO staffDAO = new StaffDAO();
    private SalaryDAO salaryDAO = new SalaryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String staffIdParam = request.getParameter("staffID");

        // Validate staffID parameter
        if (staffIdParam == null || staffIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid staffID.");
            return;
        }
        

        try {
            int staffId = Integer.parseInt(staffIdParam);

            // Fetch the staff and salary details using DAO methods
            Staff staff = staffDAO.getStaffByID(staffId);
            Salary salary = salaryDAO.getSalaryByStaffID(staffId);
            
            if (staff == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Staff not found.");
                return;
            }

            String viewType = request.getParameter("viewType");

            if ("salary".equalsIgnoreCase(viewType)) {
                request.setAttribute("salary", salary);
                request.setAttribute("staffID", staffId);
                request.getRequestDispatcher("foEmpSalaryDetails.jsp").forward(request, response);
            } else if ("overview".equalsIgnoreCase(viewType)) {
                request.setAttribute("staff", staff);
                request.setAttribute("staffID", staffId);
                request.getRequestDispatcher("foEmpOverview.jsp").forward(request, response);
            } else {
                request.setAttribute("payslip", staff);
                request.setAttribute("staffID", staffId);
                request.getRequestDispatcher("foEmpPayslips.jsp").forward(request, response);
            }
        }
           
        catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid staffID format.");
        }  
    }
}

package servlet;

import dao.PayrunDAO;
import dao.PayrunDetailDAO;
import model.Payrun;
import model.PayrunDetails;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import util.DBConnection;

public class CreatePayrunServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assuming you get these from the request or session
        String monthParam = request.getParameter("payrunMonth");
        String yearParam = request.getParameter("payrunYear");

        if (monthParam == null || monthParam.isEmpty() || yearParam == null || yearParam.isEmpty()) {
            request.setAttribute("error", "Month or Year not provided.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        System.out.println("payrunMonth = " + request.getParameter("payrunMonth"));
        System.out.println("payrunYear = " + request.getParameter("payrunYear"));

        int month = Integer.parseInt(monthParam);
        int year = Integer.parseInt(yearParam);
        String createdBy = (String) request.getSession().getAttribute("username"); // or however you get current user

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();

            PayrunDAO payrunDAO = new PayrunDAO(conn);
            int payrunID = payrunDAO.createPayrun(year, month, createdBy);

            PayrunDetailDAO detailDAO = new PayrunDetailDAO();
            List<PayrunDetails> detailList = detailDAO.generateAndInsertPayrunDetails(payrunID, String.valueOf(month), String.valueOf(year));

            // Optionally create Payrun object for forwarding (if you need it)
            Payrun payrun = new Payrun();
            payrun.setMonth(month);
            payrun.setYear(year);

            request.setAttribute("payrun", payrun);
            request.setAttribute("detailList", detailList);
            request.getRequestDispatcher("foPayrunSummary.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to create pay run.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
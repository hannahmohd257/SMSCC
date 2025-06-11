/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package servlet;

import dao.OvertimeDAO;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.OvertimeRequest;

public class OvertimeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = (String) request.getSession().getAttribute("userID");
        LocalDate otDate = LocalDate.parse(request.getParameter("otDate"));
        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
        LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));
        String reason = request.getParameter("reason");

        Duration duration = Duration.between(startTime, endTime);
        if (duration.toHours() > 2 || duration.toMinutes() <= 0) {
            request.setAttribute("error", "Overtime must be between 0 and 2 hours.");
            request.getRequestDispatcher("staffOvertimeRequest.jsp").forward(request, response);
            return;
        }

        OvertimeRequest ot = new OvertimeRequest(userID, otDate, startTime, endTime, reason);
        OvertimeDAO dao = new OvertimeDAO();
        dao.submitRequest(ot);
        response.sendRedirect("staffDashboard.jsp");
    }
}

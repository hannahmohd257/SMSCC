/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.OvertimeDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageOvertimeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int overtimeID = Integer.parseInt(request.getParameter("overtimeID"));
        String action = request.getParameter("action");
        
        // Get HR userID as String (assuming HR also uses String ID)
        String hrID = (String) request.getSession().getAttribute("userID");
        
        String remarks = action.equals("reject") ? "Rejected by HR" : "Approved by HR";

        OvertimeDAO dao = new OvertimeDAO();
        dao.updateStatus(overtimeID, action.equals("approve") ? "Approved" : "Rejected", hrID, remarks);

        response.sendRedirect("hrOvertimeApproval.jsp");
    }
}

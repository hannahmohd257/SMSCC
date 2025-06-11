/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.PayrunDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Payrun;
import model.PayrunDetails;
import util.DBConnection;

public class PayrunServlet extends HttpServlet {
    private PayrunDAO payrunDAO;

    @Override
    public void init() throws ServletException {
        super.init(); // optional but good practice
        try {
            Connection conn = DBConnection.getConnection();
            payrunDAO = new PayrunDAO(conn);
        } catch (Exception e) {
            throw new ServletException("DB connection initialization failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String payrunIDParam = request.getParameter("payrunID");

        if (payrunIDParam != null) {
            // Show summary of a specific payrun
            try {
                int payrunID = Integer.parseInt(payrunIDParam);
                Payrun payrun = payrunDAO.getPayrunById(payrunID);
                List<PayrunDetails> details = payrunDAO.getPayrunDetails(payrunID);

                request.setAttribute("payrun", payrun);
                request.setAttribute("payrunDetails", details);

                RequestDispatcher dispatcher = request.getRequestDispatcher("foPayrunSummary.jsp");
                dispatcher.forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect("PayrunServlet"); // fallback if invalid ID
            } catch (SQLException ex) {
                Logger.getLogger(PayrunServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Show all payruns (list view)
            List<Payrun> payrunList = payrunDAO.getAllPayruns();
            request.setAttribute("payrunList", payrunList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("foPayrunList.jsp");
            dispatcher.forward(request, response);
        }
    }
}
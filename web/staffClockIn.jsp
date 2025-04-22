<%-- 
    Document   : staffClocking
    Created on : 21 Jan 2025, 7:01:33?am
    Author     : user
--%>

<%@ page import="java.util.*, dao.AttendanceDAO, model.Attendance" %>
<%@ page import="dao.AttendanceDAO" %>
<%@ page import="model.Attendance" %>
<%@ page import="java.util.*" %>
<%
    String message = (String) session.getAttribute("message");
    if (message != null) {
%>
    <p style="color: green;"><strong><%= message %></strong></p>
<%
        session.removeAttribute("message"); // clear it after showing
    }
%>


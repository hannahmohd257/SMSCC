<%-- 
    Document   : foPayrunSummary
    Created on : 18 May 2025, 4:48:47?am
    Author     : user
--%>

<%@ page import="java.util.*, model.*" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Pay Run Summary</title>
</head>
<body>
    <h2>Pay Run Summary</h2>
    <c:set var="summary" value="${summary}" />
    <p><strong>Month:</strong> ${summary.month}</p>
    <p><strong>Year:</strong> ${summary.year}</p>
    <p><strong>Created Date:</strong> ${summary.createdDate}</p>
    <p><strong>Status:</strong> ${summary.status}</p>
    <p><strong>Total Salary:</strong> RM ${summary.totalSalary}</p>

    <div class="content">
        <div class="header">
            <h1>Pay Run Summary - <span class="name">${month}/${year}</span></h1>
        </div>

        <div class="section">
            <h2>Employee Pay Breakdown</h2>
            <table border="1" cellpadding="10" cellspacing="0">
                <tr>
                    <th>Staff ID</th>
                    <th>Name</th>
                    <th>Basic Salary</th>
                    <th>OT</th>
                    <th>Deductions</th>
                    <th>Total Pay</th>
                </tr>
                <c:forEach var="item" items="${details}">
                    <tr>
                        <td>${item.staffID}</td>
                        <td>${item.name}</td>
                        <td>RM ${item.basicSalary}</td>
                        <td>RM ${item.overtime}</td>
                        <td>RM ${item.deductions}</td>
                        <td><strong>RM ${item.totalPay}</strong></td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${not empty payrunID}">
                <p style="color: green;">Pay Run #${payrunID} created successfully!</p>
            </c:if>
        </div>
    </div>
    <br/>
    <a href="foPayrunList.jsp">Back to Pay Run List</a>
</body>
</html>
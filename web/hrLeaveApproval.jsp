<%-- 
    Document   : hrLeaveApprova
    Created on : 30 May 2025, 5:34:02?pm
    Author     : user
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.LeaveRequest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>CC | Pending Leave Requests</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 1100px;
            margin: 40px auto;
            background-color: #ffffff;
            padding: 30px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.1);
            border-radius: 12px;
        }

        h2 {
            margin-bottom: 20px;
            color: #333;
        }

        .message {
            margin-bottom: 15px;
            padding: 12px;
            border-radius: 8px;
            background-color: #e0f7e9;
            color: #256029;
            border: 1px solid #a5d6a7;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 14px;
            text-align: left;
            border-bottom: 1px solid #ddd;
            font-size: 14px;
        }

        th {
            background-color: #f1f1f1;
            color: #333;
        }

        td {
            vertical-align: top;
        }

        .btn {
            padding: 8px 14px;
            font-size: 13px;
            border-radius: 8px;
            border: none;
            cursor: pointer;
            margin-right: 6px;
        }

        .btn-approve {
            background-color: #4caf50;
            color: #fff;
        }

        .btn-reject {
            background-color: #f44336;
            color: #fff;
        }

        .btn:hover {
            opacity: 0.9;
        }

        .action-form {
            display: inline;
        }

        .datetime-block {
            line-height: 1.4;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Pending Leave Requests</h2>

        <c:if test="${not empty sessionScope.hrLeaveMessage}">
            <div class="message">
                ${sessionScope.hrLeaveMessage}
            </div>
            <c:remove var="hrLeaveMessage" scope="session" />
        </c:if>

        <c:choose>
            <c:when test="${empty pendingRequests}">
                <p>No pending leave requests at the moment.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Staff Name</th>
                            <th>Leave Type</th>
                            <th>Duration</th>
                            <th>Start</th>
                            <th>End</th>
                            <th>Reason</th>
                            <th>Request Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="lr" items="${pendingRequests}">
                            <tr>
                                <td>${lr.fullname}</td>
                                <td>${lr.leaveType}</td>
                                <td>${lr.durationType}</td>
                                <td class="datetime-block">
                                    <c:choose>
                                        <c:when test="${lr.durationType eq 'partial'}">
                                            ${lr.startDate}<br>
                                            ${lr.fromTime} - ${lr.toTime}
                                        </c:when>
                                        <c:otherwise>
                                            ${lr.startDate}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${lr.endDate}</td>
                                <td>${lr.reason}</td>
                                <td>${lr.requestDate}</td>
                                <td>
                                    <form class="action-form" action="ManageLeaveServlet" method="post">
                                        <input type="hidden" name="requestID" value="${lr.requestID}">
                                        <input type="hidden" name="action" value="approve">
                                        <button type="submit" class="btn btn-approve">Approve</button>
                                    </form>
                                    <form class="action-form" action="ManageLeaveServlet" method="post">
                                        <input type="hidden" name="requestID" value="${lr.requestID}">
                                        <input type="hidden" name="action" value="reject">
                                        <button type="submit" class="btn btn-reject">Reject</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>

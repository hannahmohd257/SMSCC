<%-- 
    Document   : userAddFailed
    Created on : 20 May 2025, 5:47:48?pm
    Author     : user
--%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Retrieve the error message from the session
    String errorMessage = (String) session.getAttribute("errorMessage");
    session.removeAttribute("errorMessage"); // Clear after use
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Add Failed</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #f4f4f9;
        }
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }
        .modal-content {
            background-color: #dc3545;
            color: white;
            padding: 20px;
            border-radius: 5px;
            text-align: center;
            max-width: 400px;
            width: 100%;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.25);
        }
        .modal-content p {
            font-size: 18px;
            margin: 0 0 20px;
        }
        .modal-content button {
            padding: 10px 20px;
            border: none;
            background-color: #fff;
            color: #dc3545;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }
        .modal-content button:hover {
            background-color: #e6e6e6;
        }
    </style>
</head>
<body>
    <div class="modal" id="failModal">
        <div class="modal-content">
            <p><%= errorMessage != null ? errorMessage : "Failed to add user. Please try again." %></p>
            <button onclick="goBack()">Back to Form</button>
        </div>
    </div>

    <script>
        window.onload = function () {
            const modal = document.getElementById("failModal");
            modal.style.display = "flex";
        };

        function goBack() {
            window.history.back(); // Or change to specific form page if needed
            // window.location.href = "foAddEmployee1.jsp";
        }
    </script>
</body>
</html>


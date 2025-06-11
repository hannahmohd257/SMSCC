<%-- 
    Document   : employeeSuccess
    Created on : 20 Jan 2025, 1:53:23?am
    Author     : user
--%>

<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Retrieve the success message and role from the session
    String message = (String) session.getAttribute("message");
    String role = (String) session.getAttribute("newUserRole"); // You must set this in your servlet
    session.removeAttribute("message");
    session.removeAttribute("newUserRole"); // Clear the role after use
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Added Successfully</title>
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
            background-color: #28a745;
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
            color: #28a745;
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
    <div class="modal" id="successModal">
        <div class="modal-content">
            <p><%= message != null ? message : "User added successfully!" %></p>
            <button onclick="redirectToUserList()">Go to User List</button>
        </div>
    </div>

    <script>
        window.onload = function () {
            const modal = document.getElementById("successModal");
            modal.style.display = "flex";
        };

        function redirectToUserList() {
            const role = "<%= role != null ? role : "" %>".toLowerCase();
            let url = "UserListServlet";

            switch (role) {
                case "staff":
                    url += "?role=Staff";
                    break;
                case "manager":
                    url += "?role=Manager";
                    break;
                case "human resource":
                    url += "?role=Human Resource";
                    break;
                case "finance officer":
                    url += "?role=Finance Officer";
                    break;
                default:
                    url += "?role=Staff"; // fallback
            }

            window.location.href = url;
        }
    </script>
</body>
</html>

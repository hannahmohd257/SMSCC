<%-- 
    Document   : logout
    Created on : 20 Jan 2025, 1:32:28 am
    Author     : user
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Logout Confirmation</title>
    <script type="text/javascript">
        // Function to confirm logout action
        function confirmLogout() {
            var confirmation = confirm("Are you sure you want to log out?");
            if (confirmation) {
                // Log out and redirect to login page
                window.location.href = "login.jsp"; // Redirect to login page
            }
            // If user clicks "No" (Cancel), nothing happens, so the user stays on the current page
        }

        // Trigger logout confirmation when the page loads
        window.onload = function() {
            confirmLogout(); // This will prompt the confirmation dialog when the page loads
        }
    </script>
</head>
<body>
    <!-- The page will remain on the current page if the user clicks 'No' -->
</body>
</html>

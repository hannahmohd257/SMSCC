<%-- 
    Document   : Login
    Created on : 14 Jan 2025, 11:48:38?pm
    Author     : user
--%>

<!DOCTYPE html>
<html>
<head>
    <title>CC | Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-size: 14px;
            margin-bottom: 5px;
            color: #555;
        }
        input[type="text"],
        input[type="password"],
        select {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: red;
            font-size: 12px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>
        <form action="LoginServlet" method="post">
            <div class="form-group">
                <label for="staffID">Staff ID</label>
                <input type="text" id="staffID" name="staffID" required>
            </div>
            <div class="form-group">
                <label for="staffPassword">Password</label>
                <input type="password" id="staffPassword" name="staffPassword" required>
            </div>
            <div class="form-group">
                <label for="staffRole">Role</label>
                <select id="staffRole" name="staffRole" required>
                    <option value="1">General Staff</option>
                    <option value="2">Finance Officer</option>
                    <option value="3">Manager</option>
                </select>
            </div>
            <button type="submit">Login</button>
            <c:if test="${not empty errorMessage}">
                <p class="error-message">${errorMessage}</p>
            </c:if>
        </form>
    </div>
</body>
</html>

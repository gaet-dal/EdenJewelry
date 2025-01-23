<%--
  Created by IntelliJ IDEA.
  User: luigi
  Date: 23/01/2025
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Eden Jewelry</title>
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style/loginStyle.css">
</head>
<body>
<img src="<%= request.getContextPath() %>/images/apple.png" alt="Eden" class="background-image">
<div class="header">
    <span class="menu-icon">&#9776;</span>
    <img src="<%= request.getContextPath() %>/logo.png" alt="Eden Jewelry Logo">
    <div>
        <span class="menu-icon">&#128100;</span>
        <span class="menu-icon">&#128722;</span>
    </div>
</div>

<div class="login-container">
    <div class="login-box">
        <form action="<%= request.getContextPath() %>/login" method="post">
            <input type="email" name="email" placeholder="E-mail" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Accedi ora!</button>
            <p>Non sei registrato? <a href="<%= request.getContextPath() %>/register">Clicca qui!</a></p>
        </form>
        <%
            // messaggi di errore o successo
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <p style="color: red;"><%= errorMessage %></p>
        <%
            }
        %>
    </div>
</div>
</body>
</html>


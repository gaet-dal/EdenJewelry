<%--
  Created by IntelliJ IDEA.
  User: luigi
  Date: 23/01/2025
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="main.java.dataManagement.bean.UtenteBean" %>
<jsp:useBean id="utente" class="main.java.dataManagement.bean.UtenteBean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%
    // Controlla se l'utente è già loggato
    if (utente != null && utente.getEmail() != null && !utente.getEmail().isEmpty()) {
        String tipo=utente.getTipo(); //recuperiamo il ripo dell'utente

        if("user".equals(tipo)){
            response.sendRedirect("ProfiloUtente.jsp");
        }
        else if("seller".equals(tipo))
            response.sendRedirect("ProfiloVenditore.jsp");



    }
%>

<html lang="it">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Eden Jewelry</title>
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/loginStyle1.css">
</head>

<body>

<!-- Verifica del contesto dell'applicazione -->
<%
    String contextPath = request.getContextPath();
%>

<img src="${pageContext.request.contextPath}/assets/images/apple.png" alt="Eden" class="background-image">
<header>
    <a href="${pageContext.request.contextPath}/HomeServlet">
        <img src="${pageContext.request.contextPath}/assets/images/logo1.png" alt="Eden Jewelry">
    </a>
    <div class="icons">
        <a href="profiloUtente.jsp">
            <img src="${pageContext.request.contextPath}/assets/images/user-icon.png">
        </a>
        <p>Profilo</p>
        <a href="wishlist.jsp">
            <img src="${pageContext.request.contextPath}/assets/images/wishlist-icon.png">
        </a>
        <p>Wishlist</p>
        <a href="carrello.jsp">
            <img src="${pageContext.request.contextPath}/assets/images/cart-icon.png"></a>
        <p>Carrello</p>
    </div>
</header>
<!-- Mostra messaggio di errore di login, se presente -->
<%-- Controlla se è stato impostato un messaggio di errore di login --%>


<!--form per il login -->
<div class="login-container">
    <div class="login-box">
        <form action="<%= request.getContextPath() %>/LoginServlet" method="post">
            <input type="email" name="email" placeholder="E-mail" required>
            <input type="password" name="password" placeholder="Password" required>

            <% if (request.getAttribute("login-error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("login-error") %>
            </div>
            <% } %>

            <button type="submit">Accedi ora!</button>
            <p>Non hai un account? <a class="link1" href="<%= contextPath %>/script/registazione.jsp">Registrati qui </a></p>
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

<!-- Mostra messaggio di errore di login, se presente -->
<%-- Controlla se è stato impostato un messaggio di errore di login --%>
<% if (request.getAttribute("login-error") != null) { %>
<div class="alert alert-danger" role="alert">
    <%= request.getAttribute("login-error") %>
</div>
<% } %>
</body>
</html>


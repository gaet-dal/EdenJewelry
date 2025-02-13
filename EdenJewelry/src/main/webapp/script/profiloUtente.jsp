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
    String tipo=null;
    // Controlla se l'utente è già loggato
    if (utente != null && utente.getEmail() != null && !utente.getEmail().isEmpty()) {
         tipo = utente.getTipo(); //recuperiamo il ripo dell'utente
        String nome = utente.getNome();

        if(tipo.equals("seller")){
            response.sendRedirect("ProfiloVenditore.jsp"); //se non è loggato, si reindirizza al login;
        }
    }
    else {
        response.sendRedirect("login.jsp"); //se non è loggato, si reindirizza al login;
    }


%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profilo - EdenJewelry</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&icon_names=login" />
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/profiloStyle.css">
</head>
<body>
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

<div class="profile-container">
    <div class="profile-box">
        <div class="user-profile">
            <div class="profile-header">
                <div class="profile-icon">
                    <span class="menu-icon">&#128100;</span>
                </div>
                <div class="profile-info">
                    <!--recupera il nome del utente dalla sessione e lo stampa-->
                    <h3><%= utente.getNome() != null ? utente.getNome()  : "Nome Utente" %></h3>
                    <a href="<%= request.getContextPath() %>/profile" class="profile-link">Visualizza profilo &#8594;</a>
                </div>
            </div>
        </div>

        <!--creare dei bottoni per visualizzare wishlist e ordini-->

        <form action="${pageContext.request.contextPath}/WishlistServlet" method="post">
            <input type="hidden" name="email" value="<%= utente.getEmail() %>">
            <button class="profile-button" name="lista_desideri" type="submit" value="view">Lista Desideri</button>
        </form>

        <!-- correggere la servet a cui collegare gli ordini-->
        <form action="${pageContext.request.contextPath}/CheckoutServlet" method="post">
            <button name="RiepilogoOrdine" value="review" class="profile-button" type="submit" >I Miei Ordini</button> <!-- Reindirizza alla servlet per lo storico -->
        </form>

        <p class="logout">
        <form action="<%= request.getContextPath() %>/LoginServlet" method="post">
            <input type="hidden" name="action" value="logout">
            <button type="submit" class="profile-button">Log out &#8594;</button>
        </form>
        </p>

    </div>
</div>
</body>
</html>

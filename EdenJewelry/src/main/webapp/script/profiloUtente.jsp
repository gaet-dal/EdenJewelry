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
        String tipo = utente.getTipo(); //recuperiamo il ripo dell'utente
        String nome = utente.getNome();
    }
    else {
        response.sendRedirect("login.jsp"); //se non è loggato, si reindirizza al login;

      /*  if("user".equals(tipo)){
            response.sendRedirect("ProfiloUtente.jsp");
        }
        else
            response.sendRedirect("ProfiloVenditore.jsp");
        */

    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profilo Utente</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&icon_names=login" />
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style/profiloStyle.css">
</head>
<body>
<img src="<%= request.getContextPath() %>/images/apple.png" alt="Decorazione" class="background-image">
<header>
    <img src="<%= request.getContextPath() %>/images/logo1.png" alt="Eden Jewelry">
    <div class="icons">
        <img src="<%= request.getContextPath() %>/images/user-icon.png">
        <p>Profilo</p>
        <img src="<%= request.getContextPath() %>/images/wishlist-icon.png">
        <p>Wishlist</p>
        <img src="<%= request.getContextPath() %>/images/cart-icon.png">
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
                    <h3><%= session.getAttribute("nome") != null ? session.getAttribute("nome") : "Nome Utente" %></h3>
                    <a href="<%= request.getContextPath() %>/profile" class="profile-link">Visualizza profilo &#8594;</a>
                </div>
            </div>
        </div>

        <!--creare dei bottoni per visualizzare wishlist e ordini-->

        <form action="${pageContext.request.contextPath}/WishlistServlet" method="post">
            <input type="hidden" name="email" value="<%= utente.getEmail() %>">
            <button name="lista_desideri" type="submit" value="view">Lista Desideri</button>
        </form>

        <!-- correggere la servet a cui collegare gli ordini-->
        <form action="${pageContext.request.contextPath}/Ordini" method="post">
            <input type="hidden" name="email" value="<%= utente.getEmail() %>">
            <button name=confema_ordine type="submit" value="view" >I Miei Ordini</button> <!-- Reindirizza alla servlet per lo storico -->
        </form>

        <p class="logout">
            <!--non dobbiamo passare alcun parametro--->
            <!--semplicemente viene invalidata la sessione quando l'utente clicca su logout-->
            <!--diamo il valore logout alla action, in modo da poter richiamare solo lo specifico metodo nella servlet del login--->
            <a href="<%= request.getContextPath() %>/LoginServlet?action=logout" class="logout-link">Log out &#8594;</a>
        </p>

    </div>
</div>
</body>
</html>

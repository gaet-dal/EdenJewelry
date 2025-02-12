<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="main.java.dataManagement.bean.UtenteBean" %>
<jsp:useBean id="utente" class="main.java.dataManagement.bean.UtenteBean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.sql.DataSource" %>

<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.java.dataManagement.dao.WishlistDAO" %>
<%@ page import="main.java.dataManagement.bean.WishlistBean" %>
<%@ page import="main.java.dataManagement.bean.ProdottoBean" %>
<%@ page import="main.java.dataManagement.bean.ItemWishlistBean" %>
<%@ page import="main.java.application.gestioneAcquisti.Carrello" %>
<%@ page import="main.java.application.gestioneAcquisti.ItemCarrello" %>

<%! Carrello cart = null;
    List<ItemCarrello> items = null;
    float totale = 0.0f;
%>

<%
    // Controlla se l'utente è già loggato
    if (utente != null && utente.getEmail() != null && !utente.getEmail().isEmpty()) {
        String email = utente.getEmail();
        cart = (Carrello) session.getAttribute("carrello");
        items = cart.getListProdotti();// Recuperiamo l'email dell'utente
    } else {
        // Se l'utente non è loggato, lo rimandiamo al login
        response.sendRedirect("login.jsp");
    }
%>




<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/carrelloStyle.css" type="text/css">
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
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
    </div>
</header>

<div class="center-wrapper">
    <div class="cart-container">
        <%
            if (items == null || items.isEmpty()) {
        %>
        <p>Il tuo carrello è vuoto.</p>
        <%
        } else {
            for (ItemCarrello item : items) {
                totale = (Float) request.getAttribute("totale");
        %>
        <div class="item">
            <img src="<%= request.getContextPath() %>/images/products/<%= item.getNome().replace(" ", "") %>.png" alt="<%= item.getNome() %>">
            <div class="item-info">
                <h4><%= item.getNome() %></h4>
                <p>Quantità: <%= item.getQuantità() %></p>
            </div>
            <form action="${pageContext.request.contextPath}/CarrelloServlet" method="post">
                <input type="hidden" name="prodottoId" value="<%= item.getNome() %>">
                <button name="carrello" value="elimina" type="submit" class="remove-button">Elimina</button>
            </form>
        </div>
        <%
            }
        %>
        <div class="total">Importo totale: &euro;<%= String.format("%.2f", totale) %></div>

        <form action="${pageContext.request.contextPath}/script/confermaOrdine.jsp">
            <input type="hidden" name="totale" value="<%= totale %>">
            <button name="RiepilogoOrdine" value="view" type="submit" class="checkout-button">Procedi all'acquisto</button>
        </form>

        <%
            }
        %>
    </div>
</div>
</body>
</html>
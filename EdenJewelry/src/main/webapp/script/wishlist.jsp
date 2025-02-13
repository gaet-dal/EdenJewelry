<%--
  Created by IntelliJ IDEA.
  User: miria
  Date: 27/01/2025
  Time: 09:28
  To change this template use File | Settings | File Templates.
--%>
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

<%! List<ItemWishlistBean> wishlist= null;%>

<%
  // Controlla se l'utente è già loggato
  if (utente != null && utente.getEmail() != null && !utente.getEmail().isEmpty()) {
    String email = utente.getEmail(); // Recuperiamo l'email dell'utente

    wishlist = (List<ItemWishlistBean>) session.getAttribute("wishlist");
    System.out.print("wishlist nella jsp "+wishlist.toString());

  } else {
    // Se l'utente non è loggato, lo rimandiamo al login
    response.sendRedirect("login.jsp");
  }
%>

<% String contextPath = request.getContextPath();%>

  <!DOCTYPE html>
  <html lang="it">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wishlist - EdenJewelry</title>
    <link rel="stylesheet" href="<%= contextPath%>/style/wishlistStyle.css">
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
      <a href="carrello.jsp">
        <img src="${pageContext.request.contextPath}/assets/images/cart-icon.png">
      </a>
      <p>Carrello</p>
    </div>
  </header>
  <div class="center-wrapper">

  <div class="wishlist-container">
    <h1>La tua Lista Desideri</h1>
    <%-- Controlla se la wishlist è vuota --%>
    <% if (wishlist == null || wishlist.isEmpty()) { %>
    <p>La tua lista desideri è vuota.</p>
    <% } else { %>
    <div class="item">
      <%-- Itera sui prodotti della wishlist --%>
      <% for (ItemWishlistBean item : wishlist) { %>
      <div class="item-info">
        <img src="<%= contextPath %>/assets/images/products/collanaCuore.png" alt="<%= item.getNomeProdotto() %>">
        <p><strong><%= item.getNomeProdotto() %></strong></p>
      </div>
        <%-- Creiamo un form per inviare alla servlet la gestione dell'eliminazione del prodotto --%>
        <form action="<%= contextPath %>/WishlistServlet" method="post">
          <input type="hidden" name="prodottoId" value="<%= item.getNomeProdotto() %>">
          <%-- Controlliamo se ci sono degli errori nell'eliminazione di un prodotto dalla wishlist --%>
          <% if (request.getAttribute("wishlistremove-error") != null) { %>
          <%-- Se l'errore è presente, viene mostrato sotto il campo --%>
          <%= request.getAttribute("wishlistremove-error") %>
          <% } %>
          <%-- Dalla servlet, recuperiamo questa variabile per distinguere le varie operazioni --%>
          <button type="submit" name="lista_desideri" value="rimuovi">Rimuovi</button>
        </form>
      </div>
      <% } %>
    </div>
    <% } %>
  </div>
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: miria
  Date: 27/01/2025
  Time: 09:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="main.java.gestioneAccount.utente.UtenteBean" %>
<jsp:useBean id="utente" class="main.java.gestioneAccount.utente.UtenteBean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.sql.DataSource" %>

<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.java.gestioneProdotti.wishlist.WishlistDAO" %>
<%@ page import="main.java.gestioneProdotti.wishlist.WishlistBean" %>
<%@ page import="main.java.gestioneProdotti.prodotto.ProdottoBean" %>


<%!
  WishlistBean wishlist = null;
%>

<%
  // Controlla se l'utente è già loggato
  if (utente != null && utente.getEmail() != null && !utente.getEmail().isEmpty()) {
    String email = utente.getEmail(); // Recuperiamo l'email dell'utente

    // Recuperiamo il DataSource
    DataSource ds = (DataSource) application.getAttribute("MyDataSource");
    WishlistDAO wishlistDAO = new WishlistDAO(ds);

    try {
      // Recuperiamo la wishlist dal database
      wishlist = wishlistDAO.doRetrieveByEmail(email);
    } catch (SQLException e) {
      throw new RuntimeException("Errore durante il recupero della lista desideri", e);
    }
  } else {
    // Se l'utente non è loggato, lo rimandiamo al login
    response.sendRedirect("login.jsp");
  }
%>

  }
  else
    response.sendRedirect("login.jsp"); //se l'utente non è loggato, lo rimandiamo al login;
%>

<html>
<head>
    <title>WishList - Eden Jewelry</title>
</head>

<body>

  <!-- Verifica del contesto dell'applicazione -->
  <%
    String contextPath = request.getContextPath();
  %>

  <img src="<%= request.getContextPath() %>/images/apple.png" alt="Eden" class="background-image">
  <div class="header">
    <span class="menu-icon">&#9776;</span>
    <img src="<%= request.getContextPath() %>/logo.png" alt="Eden Jewelry Logo">
    <div>
      <span class="menu-icon">&#128100;</span>
      <span class="menu-icon">&#128722;</span>
    </div>
  </div>

  <div class="wishlist-container">
    <h1>La tua Lista Desideri</h1>


    <%-- Controlla se la wishlist è vuota --%>
    <% if (wishlist == null || wishlist.getProdotti() == null || wishlist.getProdotti().isEmpty()) { %>
    <p>La tua lista desideri è vuota.</p>
    <% } else { %>
    <div class="products">
      <%-- Itera sui prodotti della wishlist --%>
      <% for (ProdottoBean prodotto : wishlist.getProdotti()) { %>
      <div class="product">
        <img src="<%= contextPath %>/images/products/<%= prodotto.getImmagine() %>" alt="<%= prodotto.getNome() %>">
        <p><strong><%= prodotto.getNome() %></strong></p>
        <p>€<%= prodotto.getPrezzo() %></p>

        <%-- Creiamo un form per inviare alla servlet la gestione dell'eliminazione del prodotto --%>
        <form action="<%= contextPath %>/WishlistServlet" method="post">
          <input type="hidden" name="prodottoId" value="<%= prodotto.getNome() %>">

          <%-- Controlliamo se ci sono degli errori nell'eliminazione di un prodotto dalla wishlist --%>
          <% if (request.getAttribute("wishlistremove-error") != null) { %>
          <%-- Se l'errore è presente, viene mostrato sotto il campo --%>
          <div class="wishlistremove-message"><%= request.getAttribute("wishlistremove-error") %></div>
          <% } %>

          <%-- Dalla servlet, recuperiamo questa variabile per distinguere le varie operazioni --%>
          <button type="submit" name="WishlistAction" value="rimuovi">Rimuovi</button>
        </form>
      </div>
      <% } %>
    </div>
    <% } %>
  </div>
</body>
</html>

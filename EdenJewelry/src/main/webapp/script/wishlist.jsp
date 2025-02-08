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

  <img src="${pageContext.request.contextPath}/assets/images/apple.png" alt="Eden" class="background-image">
  <header>
    <img src="${pageContext.request.contextPath}/assets/images/logo1.png" alt="Eden Jewelry">
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
  <!DOCTYPE html>
  <html lang="it">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello</title>
    <link rel="stylesheet" href="style/carrelloStyle.css">
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
  </head>
  <body>
  <img src="images/apple.png" alt="Eden" class="background-image">
  <header>
    <img src="images/logo1.png" alt="Eden Jewelry">
    <div class="icons">
      <img src="images/user-icon.png">
      <p>Profilo</p>
      <img src="images/wishlist-icon.png">
      <p>Wishlist</p>
      <img src="images/cart-icon.png">
      <p>Carrello</p>
    </div>
  </header>
  <div class="center-wrapper">
    <div class="cart-container">
      <div class="item">
        <img src="images/products/collanaCuore.png" alt="Collana cuore">
        <div class="item-info">
          <h4>Collana cuore</h4>
          <p>&euro;23,00</p>
        </div>
        <button class="remove-button">Elimina</button>
      </div>
      <div class="item">
        <img src="images/products/collanaPiuma.png" alt="Collana piuma">
        <div class="item-info">
          <h4>Collana piuma</h4>
          <p>&euro;17,00</p>
        </div>
        <button class="remove-button">Elimina</button>
      </div>

      <div class="item">
        <img src="images/products/collanaElizabeth.png" alt="Collana Mary">
        <div class="item-info">
          <h4>Collana Mary</h4>
          <p>&euro;27,00</p>
        </div>
        <button class="remove-button">Elimina</button>
      </div>

      <div class="total">Importo totale: &euro;67,00</div>
      <button class="checkout-button">Procedi all'acquisto</button>
    </div>
  </div>
  </body>
  </html>

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
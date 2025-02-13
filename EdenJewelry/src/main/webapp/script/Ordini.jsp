<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="utente" class="main.java.dataManagement.bean.UtenteBean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.java.dataManagement.dao.WishlistDAO" %>
<%@ page import="main.java.application.gestioneAcquisti.Carrello" %>
<%@ page import="main.java.application.gestioneAcquisti.ItemCarrello" %>
<%@ page import="main.java.dataManagement.bean.*" %>

<%
  List<RigaOrdineBean> righe = (List<RigaOrdineBean>) request.getAttribute("righe");
  List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("ordini");
%>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Ordini Effettuati - Eden Jewelry</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/style/ordiniStyle1.css" type="text/css">
  <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
</head>
<body>
<img src="${pageContext.request.contextPath}/assets/images/apple.png" alt="Eden" class="background-image">
<header>
  <a href="${pageContext.request.contextPath}/HomeServlet">
    <img src="${pageContext.request.contextPath}/assets/images/logo1.png" alt="Eden Jewelry">
  </a>
  <div class="icons">
    <a href="${pageContext.request.contextPath}/script/profiloUtente.jsp">
      <img src="${pageContext.request.contextPath}/assets/images/user-icon.png">
    </a>
    <p>Profilo</p>
    <a href="${pageContext.request.contextPath}/script/wishlist.jsp">
      <img src="${pageContext.request.contextPath}/assets/images/wishlist-icon.png">
    </a>
    <p>Wishlist</p>
    <a href="${pageContext.request.contextPath}/script/carrello.jsp">
      <img src="${pageContext.request.contextPath}/assets/images/cart-icon.png"></a>
    <p>Carrello</p>
  </div>
</header>

<div class="container">
  <div class="orders">
    <h2>Ordini Effettuati:</h2>

    <% if (ordini != null && !ordini.isEmpty()) {
      for (OrdineBean ordine : ordini) {
    %>
    <div class="order-card">
      <div>
        <p><strong>Numero Ordine: <%= ordine.getIdOrdine() %></strong></p>
        <p><strong>E-mail: <%= ordine.getEmail() %></strong></p>  <!-- Aggiungi l'email dell'ordine -->
        <p><strong>Indirizzo: <%= ordine.getIndirizzo() %></strong></p> <!-- Aggiungi l'indirizzo dell'ordine -->

        <!-- Dettagli dell'ordine -->
        <div class="order-details">
          <% for (RigaOrdineBean riga : righe) {
            if (riga.getNumeroOrdine() == ordine.getIdOrdine()) {
          %>
          <p>Prodotto: <%= riga.getNomeProdotto() %></p>
          <p>Quantità: <%= riga.getQuantità() %></p>
          <p>Prezzo unitario: €<%= String.format("%.2f", riga.getPrezzoUnitario()) %></p>
          <p>Totale riga: €<%= String.format("%.2f", riga.getPrezzoUnitario() * riga.getQuantità()) %></p>
          <% } } %>
        </div>
      </div>
    </div>
    <%  }
    } else { %>
    <p>Non hai effettuato ancora nessun ordine.</p>
    <% } %>

    <div class="expand">Espandi</div>
  </div>
</div>
</body>
</html>

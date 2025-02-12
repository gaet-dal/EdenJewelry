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
%>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>I Miei Ordini - Eden Jewelry</title>
  <link rel="stylesheet" href="style/storicoOrdiniStyle.css">
  <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
</head>
<body>
<img src="images/apple.png" alt="Eden" class="background-image">
<header>
  <a href="${pageContext.request.contextPath}/HomeServlet">
    <img src="${pageContext.request.contextPath}/assets/images/logo1.png" alt="Eden Jewelry">
  </a>
</header>

<div class="container">
  <div class="orders">
    <h2>I miei ordini:</h2>

    <% if (righe != null && !righe.isEmpty()) {
      for (RigaOrdineBean riga : righe) {
    %>
    <div class="order-card">
      <div>
        <p><strong>Numero Ordine: <%= riga.getNumeroOrdine() %></strong></p>
        <div class="order-details">
          <p>Prodotto: <%= riga.getNomeProdotto() %></p>
          <p>Quantità: <%= riga.getQuantità() %></p>
          <p>Prezzo unitario: €<%= String.format("%.2f", riga.getPrezzoUnitario()) %></p>
          <p>Totale riga: €<%= String.format("%.2f", riga.getPrezzoUnitario() * riga.getQuantità()) %></p>
        </div>
      </div>
      <a href="#">Dettagli ordine</a>
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

<%--
  Created by IntelliJ IDEA.
  User: luigi
  Date: 27/01/2025
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Storico Ordini</title>
    <link rel="stylesheet" href="style/storicoOrdiniStyle.css">
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
</head>
<body>
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

<div class="container">
    <div class="orders">
        <h2>I miei ordini:</h2>
        <c:forEach var="ordine" items="${ordini}">
            <div class="order-card">
                <div>
                    <p><strong>N. <%= ordine.getId() %></strong></p>
                    <div class="order-details">
                        <p>Data dell'ordine: <%= ordine.getData() %></p>
                        <p>Importo totale: &euro;<%= ordine.getImporto() %></p>
                    </div>
                </div>
                <a href="dettagliOrdine.jsp?id=<%= ordine.getId() %>">Dettagli ordine</a>
            </div>
        </c:forEach>
        <div class="expand">Espandi</div>
    </div>
</div>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="main.java.dataManagement.dao.ProdottoDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.dataManagement.bean.ProdottoBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.SQLException" %>

<% List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti");%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage - EdenJewelry</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/catalogoStyle.css" type="text/css">
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

<div class="search-bar">
        <input type="text" placeholder="Cerca">
</div>

<div class="container">

    <div class="section">
        <h2>CATALOGO</h2>
        <div class="box">
            <div class="products">
                <% if (prodotti != null && !prodotti.isEmpty()) { %>
                <% for (ProdottoBean prodotto : prodotti) { %>
                <div class="product">
                    <!--PER GIGI: controllare come far recuperare l'immagine--->
                    <img src="images/products/<%= prodotto.getImmagine() %>" alt="<%= prodotto.getNome() %>">
                    <p><%= prodotto.getNome() %></p>
                    <p>€<%= String.format("%.2f", prodotto.getPrezzo()) %></p>
                </div>
                <% } %>
                <% } else { %>
                <p>Nessun prodotto disponibile nel catalogo.</p>
                <% } %>
            </div>
        </div>
    </div>
</div>
</div>

</body>
</html>

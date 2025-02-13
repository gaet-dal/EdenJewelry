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
    <title>EdenJewelry</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/homepage2.css" type="text/css">
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
        <a href="https://www.instagram.com/edenjewelryofficial/">
            <img src="${pageContext.request.contextPath}/assets/images/instagram-icon.png">
        </a>
        <p>IG</p>
    </div>
</header>


<div class="search-bar">
    <form action="${pageContext.request.contextPath}/RicercaProdottoServlet" method="GET">
        <label>
            <input type="text" name="query" placeholder="Cerca" required>
        </label>
        <br>
        <button name="submitAction" value="view" type="submit"></button>
    </form>
</div>


<div class="container">

    <div class="section">
        <div class="box">
            <div class="products">
                <% if (prodotti != null && !prodotti.isEmpty()) { %>
                <% for (ProdottoBean prodotto : prodotti) { %>
                <div class="product">
                    <a href="DettagliProdottoServlet?nome=<%= prodotto.getNome() %>">
                        <img src="images/products/<%= prodotto.getImmagine() %>" alt="<%= prodotto.getNome() %>">
                        <p><%= prodotto.getNome() %></p>
                        <p>€<%= String.format("%.2f", prodotto.getPrezzo()) %></p>
                    </a>
                </div>
                <% } %>
                <% } else { %>
                <p>Nessun prodotto disponibile nel catalogo.</p>
                <% } %>
            </div>
        </div>
    </div>
</div>


</body>
</html>

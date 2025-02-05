<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="prodotti" class="main.java.dataManagement.dao.ProdottoDAO" scope="page"/>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="main.java.dataManagement.dao.ProdottoDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.dataManagement.bean.ProdottoBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.SQLException" %>

<!%
    DataSource ds=(DataSource) application.getAttribute("MyDataSource"); //recuperiamo il ds;
    ProdottoDAO prodottoDAO= new ProdottoDAO(ds); //otteniamo un collegamento al db;

    //l'aggiunta dei prodotti la facciamo da AggiungiProdotto.jsp;
    List <ProdottoBean> prodotti;
    try {

         prodotti= prodottoDAO.doRetrieveAll(); //ricerhiamo i prodotti nel database e li mandiamo in stampa;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }


%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage - EdenJewelry</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/main/webapp/assets/style/catalogoStyle.css" type="text/css">
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

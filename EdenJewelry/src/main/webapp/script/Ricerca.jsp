<%@ page import="main.java.dataManagement.bean.ProdottoBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Debug per vedere se la lista arriva correttamente -->
<%
    List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("resultQuery");
%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--qui mandiamo in stampa il nome del prodotto-->
        <title>Risultati ricerca - EdenJewelry</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/main/webapp/style/homepage2.css" type="text/css">
    </head>
<body>

<img src="${pageContext.request.contextPath}/assets/images/apple.png" alt="Eden" class="background-image">
<header>
    <img src="${pageContext.request.contextPath}/assets/images/logo1.png" alt="Eden Jewelry">
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
                <p>OPS! Non crediamo di avere l'articolo da te cercato</p>
                <% } %>
            </div>
        </div>
    </div>
</div>


</body>
</html>

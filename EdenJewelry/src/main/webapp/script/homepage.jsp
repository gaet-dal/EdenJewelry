<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/catalogoStyle.css" type="text/css">
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
        <h2>NOVITÀ</h2>
        <div class="box">
            <div class="products">
                <%-- ciclo per generare dinamicamente i prodotti novità --%>
                <%
                    String[][] novita = {
                            {"collanaCuore.png", "Collana Cuore", "23,00"},
                            {"collanaPiuma.png", "Collana Piuma", "17,00"},
                            {"collanaZirconi.png", "Collana Zirconi", "21,00"},
                            {"collanaElizabeth.png", "Collana Elizabeth", "34,00"}
                    };

                    for (String[] prodotto : novita) {
                %>
                <div class="product">
                    <img src="images/products/<%= prodotto[0] %>" alt="<%= prodotto[1] %>">
                    <p><%= prodotto[1] %></p>
                    <p>€<%= prodotto[2] %></p>
                </div>
                <% } %>
            </div>
            <div class="expand">
                <a href="#">Mostra Altro</a>
            </div>
        </div>
    </div>

    <div class="section">
        <h2>CATALOGO</h2>
        <div class="box">
            <div class="products">
                <%-- ciclo per generare dinamicamente i prodotti del catalogo --%>
                <%
                    String[][] catalogo = {
                            {"collana_ariel.jpg", "Collana Ariel", "45,00"},
                            {"collana_cuore_bianco.jpg", "Collana cuore bianco", "25,00"},
                            {"collana_kate.jpg", "Collana Kate", "25,00"},
                            {"collana_mary.jpg", "Collana Mary", "27,00"}
                    };

                    for (String[] prodotto : catalogo) {
                %>
                <div class="product">
                    <img src="images/products/<%= prodotto[0] %>" alt="<%= prodotto[1] %>">
                    <p><%= prodotto[1] %></p>
                    <p>€<%= prodotto[2] %></p>
                </div>
                <% } %>
            </div>
            <div class="expand">
                <a href="#">Mostra Altro</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>

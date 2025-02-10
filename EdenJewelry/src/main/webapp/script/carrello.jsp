<%--
  Created by IntelliJ IDEA.
  User: luigi
  Date: 23/01/2025
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/carrelloStyle.css" type="text/css">
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
<div class="center-wrapper">
    <div class="cart-container">
        <div class="item">
            <img src="${pageContext.request.contextPath}/assets/images/products/collanaCuore.png" alt="Collana cuore">
            <div class="item-info">
                <h4>Collana cuore</h4>
                <p>&euro;23,00</p>
            </div>
            <button class="remove-button">Elimina</button>
        </div>
        <div class="item">
            <img src="<%= request.getContextPath() %>/images/products/collanaPiuma.png" alt="Collana piuma">
            <div class="item-info">
                <h4>Collana piuma</h4>
                <p>&euro;17,00</p>
            </div>
            <button class="remove-button">Elimina</button>
        </div>

        <div class="item">
            <img src="<%= request.getContextPath() %>/images/products/collanaElizabeth.png" alt="Collana Mary">
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

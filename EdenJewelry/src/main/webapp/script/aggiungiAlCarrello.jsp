<%--
  Created by IntelliJ IDEA.
  User: luigi
  Date: 27/01/2025
  Time: 09:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prodotto Info</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/main/webapp/style/dettagliProdotto.css">         <!--<c:url value='/style/prodottoInfo.css' />-->
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
    <div class="content-wrapper">
        <div class="product-image">
            <img src="<c:url value='/images/products/collanaCuore.png'" alt="Collana Cuore">
        </div>
        <div class="product-details">
            <div class="product-details-text">
                <h1 class="product-title">Collana cuore</h1>
                <p class="product-price">&euro;23,00</p>
            </div>
            <button class="button wishlist">Aggiungi alla wishlist</button>
            <button class="button cart">Aggiungi al carrello</button>
        </div>
    </div>
    <div class="product-info">
        <div class="product-info-item">
            <button>INFORMAZIONI SUL PRODOTTO</button>
            <p>Diamante da tre carati</p>
        </div>
    </div>
</div>

</body>
</html>


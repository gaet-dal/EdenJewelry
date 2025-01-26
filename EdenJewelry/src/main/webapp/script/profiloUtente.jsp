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
    <title>Profilo Utente</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&icon_names=login" />
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style/profiloStyle.css">
</head>
<body>
<img src="<%= request.getContextPath() %>/images/apple.png" alt="Decorazione" class="background-image">
<header>
    <img src="<%= request.getContextPath() %>/images/logo1.png" alt="Eden Jewelry">
    <div class="icons">
        <img src="<%= request.getContextPath() %>/images/user-icon.png">
        <p>Profilo</p>
        <img src="<%= request.getContextPath() %>/images/wishlist-icon.png">
        <p>Wishlist</p>
        <img src="<%= request.getContextPath() %>/images/cart-icon.png">
        <p>Carrello</p>
    </div>
</header>

<div class="profile-container">
    <div class="profile-box">
        <div class="user-profile">
            <div class="profile-header">
                <div class="profile-icon">
                    <span class="menu-icon">&#128100;</span>
                </div>
                <div class="profile-info">
                    <h3><%= session.getAttribute("username") != null ? session.getAttribute("username") : "Nome Utente" %></h3>
                    <a href="<%= request.getContextPath() %>/profile" class="profile-link">Visualizza profilo &#8594;</a>
                </div>
            </div>
        </div>

        <button class="profile-button" onclick="location.href='<%= request.getContextPath() %>/orders'">I miei ordini</button>
        <button class="profile-button" onclick="location.href='<%= request.getContextPath() %>/wishlist'">Wishlist &#9825;</button>
        <p class="logout">
            <a href="<%= request.getContextPath() %>/logout" class="logout-link">Log out &#8594;</a>
        </p>
    </div>
</div>
</body>
</html>

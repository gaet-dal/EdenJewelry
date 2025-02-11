<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="main.java.dataManagement.bean.ProdottoBean" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="utente" class="main.java.dataManagement.bean.UtenteBean" scope="session"/>

<%
  ProdottoBean prodotto = (ProdottoBean) request.getAttribute("prodotto");
  String nome=prodotto.getNome();
%>

<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--qui mandiamo in stampa il nome del prodotto-->
    <title>Dettagli del prodotto - EdenJewelry</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/homepage.css" type="text/css">
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
    <h2><!--qui mandare in stampa il nome del prodotto--></h2>
    <div class="box">
      <div class="products">
        <% if (prodotto != null) { %>
        <div class="product">
          <!-- link per la ridirezione alla jsp dei dettagli prodotto-->
          <img src="images/products/<%= prodotto.getImmagine() %>" alt="<%= prodotto.getNome() %>">
          <p><%= prodotto.getNome() %></p>
          <p>€<%= String.format("%.2f", prodotto.getPrezzo()) %></p>

          <!--creare dei bottoni per visualizzare wishlist e ordini-->

          <form action="${pageContext.request.contextPath}/WishlistServlet" method="post">
            <!--se l'utente è null, viene ridirezionato direttamente al login-->
            <input type="hidden" name="email" value="<%= utente.getEmail() %>">
            <input type="hidden" name="prodottoId" value="<%= prodotto.getNome() %>">
            <button name="lista_desideri" type="submit" value="aggiungi">Aggiungi alla Wishlist</button>
          </form>

          <form action="${pageContext.request.contextPath}/CarrelloServlet" method="post">
            <!--se l'utente è null, viene ridirezionato direttamente al login-->
            <input type="hidden" name="email" value="<%= utente.getEmail() %>">
            <input type="hidden" name="prodottoId" value="<%= prodotto.getNome() %>">
            <button name="carrello" type="submit" value="aggiungi">Aggiungi al Carrello</button>
          </form>


        </div>
        <% } %>
      </div>
    </div>
  </div>
</div>
</body>
</html>

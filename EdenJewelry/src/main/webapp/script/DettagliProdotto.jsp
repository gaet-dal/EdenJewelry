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
  <title>More Info <%=nome%> - EdenJewelry</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/style/dettagliProdotto1.css" type="text/css">
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
      <form action="${pageContext.request.contextPath}/WishlistServlet" method="post">
        <input type="hidden" name="action" value="view">
        <button name="lista_desideri" type="submit"  value="view" style="border: none; background: none; padding: 0;">
          <img src="${pageContext.request.contextPath}/assets/images/wishlist-icon.png" alt="Wishlist">
        </button>
      </form>
      <p>Wishlist</p>
      <a href="${pageContext.request.contextPath}/script/carrello.jsp">
        <img src="${pageContext.request.contextPath}/assets/images/cart-icon.png"></a>
      <p>Carrello</p>
    </div>
</header>

<div class="container">
  <div class="content-wrapper">
    <h2><!--qui mandare in stampa il nome del prodotto--></h2>
    <% if (prodotto != null) { %>
    <div class="product-image">
      <!-- link per la ridirezione alla jsp dei dettagli prodotto-->
      <img src="${pageContext.request.contextPath}/assets/images/products/collanaCuore.png" alt="<%= prodotto.getNome() %>">

    </div>
    <div class="product-details">
      <div class="product-details-text">
        <div class="product-title"><p><%= prodotto.getNome() %></p></div>
        <div class="product-price"><p>€<%= String.format("%.2f", prodotto.getPrezzo()) %></p>
        </div>
      </div>
      <!--bottoni per visualizzare wishlist e ordini-->
      <form action="${pageContext.request.contextPath}/WishlistServlet" method="post">
        <!--se l'utente è null, viene ridirezionato direttamente al login-->
        <input type="hidden" name="email" value="<%= utente.getEmail() %>">
        <input type="hidden" name="prodottoId" value="<%= prodotto.getNome() %>">
        <div class="button wishlist">
          <div class="buttonBox">
            <button name="lista_desideri" type="submit" value="aggiungi">Aggiungi alla Wishlist</button>
          </div>
        </div>
      </form>
      <form action="${pageContext.request.contextPath}/CarrelloServlet" method="post">
        <!--se l'utente è null, viene ridirezionato direttamente al login-->
        <input type="hidden" name="prodottoId" value="<%= prodotto.getNome() %>">
        <div class="button cart">
          <div class="buttonBox">
            <button name="carrello" type="submit" value="aggiungi">Aggiungi al Carrello</button>
          </div>
        </div>
      </form>
      <div class="product-info">
        <div class="product-info-item">
          <button>INFORMAZIONI SUL PRODOTTO</button>
          <p>Diamante da tre carati</p>
        </div>
      </div>
    </div>
    <% } %>
  </div>
</div>
</body>
</html>

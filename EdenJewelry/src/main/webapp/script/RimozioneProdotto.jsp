<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="main.java.dataManagement.bean.ProdottoBean" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rimozione Prodotto - EdenJewelry</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/rimuoviProdotto1.css" type="text/css">
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
  </div>
</header>
<!-- Form per Eliminare Prodotto -->
<div class="ProdottiList">
  <form id="delete_form" action="${pageContext.request.contextPath}/RicercaProdottoServlet" method="get">
    <input type="text" name="query" placeholder="Cerca prodotto per nome">
    <button name="submitAction" type="submit" value="delete">Cerca</button>
  </form>

  <!-- Debug per vedere se la lista arriva correttamente -->
  <%
    List<ProdottoBean> list = (List<ProdottoBean>) request.getAttribute("resultQuery");
  %>

  <!-- Lista dei prodotti trovati -->
  <%
    if (list != null && !list.isEmpty()) {
  %>
  <ul>
    <%
      for (ProdottoBean bean : list) {
    %>
    <li>
      <p>Nome: <%= bean.getNome() %></p>
      <form action="${pageContext.request.contextPath}/CatalogoServlet" method="post">
        <input type="hidden" name="id" value="<%= bean.getNome() %>">
        <button name="submitAction" type="submit" value="Elimina">Elimina</button>
      </form>
    </li>
    <%
      }
    %>
  </ul>
  <%
    }
  %>
</div>

</body>
</html>
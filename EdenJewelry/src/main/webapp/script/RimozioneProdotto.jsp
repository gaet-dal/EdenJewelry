<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="main.java.dataManagement.bean.ProdottoBean" %>
<%@ page import="java.util.List" %>

<html>
<head>
  <title>Rimuovi Prodotto</title>
</head>
<body>

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
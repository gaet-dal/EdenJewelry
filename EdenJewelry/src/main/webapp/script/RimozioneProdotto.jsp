<%--
  Created by IntelliJ IDEA.
  User: miria
  Date: 26/01/2025
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="main.java.gestioneProdotti.prodotto.ProdottoBean"%>
<%@ page import="java.util.List" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="main.java.gestioneProdotti.prodotto.ProdottoDAO" %>
<%@ page import="main.java.gestioneProdotti.homepage.SimpleSearch" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>

<html>
<head>
    <title>Rimuovi Prodotto</title>
</head>
<body>

<!-- Form per Eliminare Gioco -->
<div class="ProdottiList">

  <!--  form invia una richiesta alla servlet con un parametro gameSearch per cercare giochi per nome. -->
  <!-- Una volta trovati i giochi corrispondenti, viene mostrato un elenco con ciascun gioco e un pulsante "Elimina" accanto ad ogni voce -->
  <form id="delete_form" action="${pageContext.request.contextPath}/RicercaProdottoServlet" method="post">
    <input type="text" name="query" placeholder="Cerca prodotto per nome">
    <button name="submitAction" type="submit" value="delete">cerca</button>
  </form>
  <ul>
    <!-- Mostra l'elenco dei giochi trovati per la ricerca -->
    <%
      wait(50000);
      List<ProdottoBean> list = (List<ProdottoBean>) request.getAttribute("resultQuery");

      Iterator<ProdottoBean> it = list.iterator();
      while (it.hasNext()){
        ProdottoBean bean = it.next();
    %>
    <li>
      <p>Nome: <%= bean.getNome() %></p>
      <form action="${pageContext.request.contextPath}/CatalogoVenditoreServlet" method="post" style="display:inline;">
        <input type="hidden" name="id" value="<%= bean.getNome() %>">
        <!-- Quando si preme il pulsante "Elimina" accanto a un gioco nell'elenco,
        il valore di submitAction sarà impostato automaticamente a delete,
        il che indica alla servlet di procedere con l'eliminazione del gioco corrispondente -->
        <input type="hidden" value="Elimina">
        <button name="submitAction" type="submit" value="Elimina">Elimina</button>
      </form>
    </li>
    <%
        }
    %>
  </ul>
</div>



</body>
</html>

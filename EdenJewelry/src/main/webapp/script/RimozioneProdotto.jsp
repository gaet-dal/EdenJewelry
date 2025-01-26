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

<html>
<head>
    <title>Rimuovi Prodotto</title>
</head>
<body>

<!-- Form per Eliminare Gioco -->
<div class="ProdottiList">

  <!--  form invia una richiesta alla servlet con un parametro gameSearch per cercare giochi per nome. -->
  <!-- Una volta trovati i giochi corrispondenti, viene mostrato un elenco con ciascun gioco e un pulsante "Elimina" accanto ad ogni voce -->
  <form id="delete_form" action="${pageContext.request.contextPath}/PLACEHOLDER" method="post" onsubmit="return validateForm('delete_form', ['gameSearch']);">
    <input type="text" name="query" placeholder="Cerca prodotto per nome">
    <button name="submitAction" type="submit" value="Cerca">cerca</button>
  </form>
  <ul>
    <!-- Mostra l'elenco dei giochi trovati per la ricerca -->
    <%
      DataSource ds = (DataSource) application.getAttribute("MyDataSource");

      ProdottoDAO prodottoDAO = new ProdottoDAO(ds);
      List<ProdottoBean>  prodotti = prodottoDAO.doRetrieveAll();

      SimpleSearch simple = new SimpleSearch();
      ProdottoBean prod = simple.search("")
    %>
    <li>
      <p>Nome: <%= game.get_nome() %></p>
      <form action="${pageContext.request.contextPath}/Gestione_giochi_servlet" method="post" style="display:inline;">
        <input type="hidden" name="id" value="<%= game.get_id_gioco() %>">
        <!-- Quando si preme il pulsante "Elimina" accanto a un gioco nell'elenco,
        il valore di submitAction sarà impostato automaticamente a delete,
        il che indica alla servlet di procedere con l'eliminazione del gioco corrispondente -->
        <input type="hidden" value="Elimina">
        <button name="submitAction" type="submit" value="Elimina">Elimina</button>
      </form>
    </li>
    <%
        }
      }
    %>
  </ul>
</div>



</body>
</html>

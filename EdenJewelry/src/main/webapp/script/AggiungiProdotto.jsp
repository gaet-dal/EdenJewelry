<%--
  Created by IntelliJ IDEA.
  User: miria
  Date: 26/01/2025
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="main.java.gestioneAccount.utente.UtenteBean" %>
<jsp:useBean id="utente" class="main.java.gestioneAccount.utente.UtenteBean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<html>
<head>
    <title>Aggiungi Prodotto</title>
</head>
<body>

  <h2>da questa pagina è possibile inserire un nuovo prodotto. Inserisci i dati e poi clicca sul pulsante di aggiuta in fondo.</h2>

  <!-- Verifica del contesto dell'applicazione -->
  <%
    String contextPath = request.getContextPath();
  %>

  <!-- Form per Aggiungere un prodotto -->
  <div class="form-group">
    <form id="add_form" action="${pageContext.request.contextPath}/AggiungiGioco?action=aggiunta" method="post" enctype="multipart/form-data" onsubmit="return validateForm('add_form', ['nome', 'piattaforma', 'genere'], ['g_uscita', 'm_uscita', 'a_uscita' ],['prezzo']);">
      <input type="text" name="nome" placeholder="Nome del prodotto">
      <input type="text" name="prezzo" placeholder="Prezzo">
      <input type="number" name="quantita" placeholder="Quantità disponibile">

      <select id="categoria" type="text" name="categoria">
        <option value="collane">collane</option>
        <option value="bracciali">bracciali</option>
        <option value="orecchini">orecchini</option>
      </select>

      <!--PER GAETANO:  correggere come prendere l'immagine-->
      <input type="file" name="immagine" placeholder="immagine" required accept="images/*"> <!-- Campo per caricare l'immagine del gioco -->

      <button id="agg_button" name="submitAction" type="submit"  value="Aggiungi">Conferma aggiunta prodotto</button> <!-- Pulsante per aggiungere un nuovo gioco -->
    </form>
  </div>


</body>
</html>

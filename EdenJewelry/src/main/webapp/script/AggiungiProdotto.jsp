<%--
  Created by IntelliJ IDEA.
  User: miria
  Date: 26/01/2025
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="main.java.dataManagement.bean.UtenteBean" %>
<jsp:useBean id="utente" class="main.java.dataManagement.bean.UtenteBean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<html>
<head>
    <title>Aggiungi Prodotto</title>
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
  <h2>da questa pagina è possibile inserire un nuovo prodotto. Inserisci i dati e poi clicca sul pulsante di aggiuta in fondo.</h2>

  <!-- Verifica del contesto dell'applicazione -->
  <%
    String contextPath = request.getContextPath();
  %>

  <!-- Form per Aggiungere un prodotto -->
  <div class="form-group">
    <form id="add_form" action="${pageContext.request.contextPath}/CatalogoVenditoreServlet" method="post">
      <input type="text" name="nome" placeholder="Nome del prodotto">
      <input type="text" name="prezzo" placeholder="Prezzo">
      <input type="number" name="quantita" placeholder="Quantità disponibile">

      <select id="categoria" type="text" name="categoria">
        <option value="collane">collane</option>
        <option value="bracciali">bracciali</option>
        <option value="orecchini">orecchini</option>
      </select>

      <!--PER GAETANO:  correggere come prendere l'immagine-->
      <input type="file" name="immagine" placeholder="Immagine" required accept="images/*"> <!-- Campo per caricare l'immagine del gioco -->

      <button id="agg_button" name="submitAction" type="submit"  value="Aggiungi">Conferma aggiunta prodotto</button> <!-- Pulsante per aggiungere un nuovo gioco -->
    </form>
  </div>


</body>
</html>

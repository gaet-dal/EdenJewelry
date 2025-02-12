<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="main.java.dataManagement.bean.UtenteBean,
            javax.servlet.http.HttpServletRequest,
            javax.sql.DataSource,
            java.util.List,
            java.sql.SQLException,
            main.java.dataManagement.dao.WishlistDAO,
            main.java.dataManagement.bean.WishlistBean,
            main.java.dataManagement.bean.ProdottoBean,
            main.java.dataManagement.bean.ItemWishlistBean,
            main.java.application.gestioneAcquisti.Carrello,
            main.java.application.gestioneAcquisti.ItemCarrello" %>
<jsp:useBean id="utente" class="main.java.dataManagement.bean.UtenteBean" scope="session" />

<%!
    Carrello cart = null;
    List<ItemCarrello> items = null;
    float totale = 0.0f;
%>

<%

    // Recupera il carrello dalla sessione
    cart = (Carrello) session.getAttribute("carrello");
    if (cart != null) {
        items = cart.getListProdotti();
    }

    // Se l'attributo "totale" è stato impostato nella richiesta, usalo, altrimenti calcola dal carrello
   float totale = Float.parseFloat(request.getParameter("totale"));
    System.out.println("totale "+totale);

%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Conferma Ordine</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/confermaOrdineStyle.css">
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
</head>
<body>
<img src="${pageContext.request.contextPath}/assets/images/apple.png" alt="Eden" class="background-image">

<main>
    <div class="container">
        <div class="form-container">
            <h1>Conferma ordine:</h1>
            <form action="${pageContext.request.contextPath}/CheckoutServlet" method="post">
                <div class="form-section">
                    <div class="form-spedizione">
                        <h2>Dati di spedizione</h2>
                        <label for="via">Via:</label>
                        <input type="text" id="via" name="via" placeholder="Inserisci la via" required>
                        <label for="numero">N:</label>
                        <input type="text" id="numero" name="numero" placeholder="Numero civico" required>
                    </div>
                </div>
                <div class="form-section">
                    <div class="form-pagamento">
                        <h2>Dati di pagamento</h2>
                        <label for="carta">Numero carta:</label>
                        <input type="text" id="carta" name="carta" placeholder="Numero della carta" required>
                        <label for="scadenza">Scadenza:</label>
                        <input type="text" id="scadenza" name="scadenza" placeholder="MM/AA" required>
                        <label for="cvv">CVV:</label>
                        <input type="text" id="cvv" name="cvv" placeholder="CVV" required>
                    </div>
                </div>
                <button name="RiepilogoOrdine" value="view" type="submit" class="purchase-button">Acquista ora!</button>
            </form>
        </div>
        <div class="order-summary-container">
            <h2>Riepilogo ordine:</h2>
            <%
                if (items == null || items.isEmpty()) {
            %>
            <p>Il tuo carrello è vuoto.</p>
            <%
            } else {
                for (ItemCarrello item : items) {
            %>
            <div class="item">
                <img src="${pageContext.request.contextPath}/images/products/<%= item.getNome().replace(" ", "") %>.png" alt="<%= item.getNome() %>">
                <div class="item-info">
                    <h4><%= item.getNome() %></h4>
                    <p>Quantità: <%= item.getQuantità() %></p>
                </div>
            </div>
            <%
                }
            %>
            <div class="total">Importo totale: &euro;<%= String.format("%.2f", totale) %></div>
            <%
                }
            %>
        </div>
    </div>
</main>
</body>
</html>

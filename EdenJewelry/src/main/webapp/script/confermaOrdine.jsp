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
    <title>Conferma Ordine</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/confermaOrdineStyle.css">
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap" rel="stylesheet">
</head>
<body>
<img src="<%= request.getContextPath() %>/images/apple.png" alt="Eden" class="background-image">
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
<main>
    <div class="container">
        <div class="form-container">
            <h1>Conferma ordine:</h1>
            <form action="<%= request.getContextPath() %>/confermaOrdine" method="post">
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
                <button type="submit" class="purchase-button">Acquista ora!</button>
            </form>
        </div>
        <div class="order-summary-container">
            <h2>Riepilogo ordine:</h2>
            <div class="order-summary">
                <img src="<%= request.getContextPath() %>/images/products/collanaCuore.png" alt="Collana cuore">
                <p>Collana cuore - &euro;23,00</p>
            </div>
            <p class="total">Importo totale: &euro;67,00</p>
        </div>
    </div>
</main>
</body>
</html>

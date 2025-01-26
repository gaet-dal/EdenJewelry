<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - EdenJewelry</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style/loginStyle.css"> <!--correggere il css collegato-->

</head>

<body>
<!-- Verifica del contesto dell'applicazione -->
<%
    String contextPath = request.getContextPath();
%>

<div class="register-container"> <!-- questi sono i nomi che diamo dal css-->
    <div class="register-box">  <!-- GIGI MODIFICA I NOMI IN BASE A QUELLI DEL CSS-->

        <h3>Registrazione:</h3>
        <form action="<%= request.getContextPath() %>/RegistrazioneServlet" method="post"> <!--qui bisogna ricontrollare da che cartella parte il contesto-->

            <input type="text" name="nome" placeholder="Nome" required>
            <!--controlliamo se si è generato un errore sul formato della stringa del nome-->
            <% if (request.getAttribute("nome-error") != null) { %>
            <!--se l'errore è presente, viene mostrato sotto il campo-->
            <div class="error-message"><%= request.getAttribute("nome-error") %></div> <!--mandiamo in stampa l'errore che si è verificato-->
            <% } %>

            <input type="text" name="cognome" placeholder="Cognome" required>
            <!--controlliamo se si è generato un errore sul formato della stringa del nome-->
            <% if (request.getAttribute("cognome-error") != null) { %>
            <div class="error-message"><%= request.getAttribute("cognome-error") %></div> <!--mandiamo in stampa l'errore che si è verificato-->
            <% } %>

            <input type="email" name="email" placeholder="E-mail" required>
            <!--controlliamo se si è generato un errore sul formato della stringa del nome-->
            <% if (request.getAttribute("email-error") != null) { %>
            <div class="error-message"><%= request.getAttribute("email-error") %></div> <!--mandiamo in stampa l'errore che si è verificato-->
            <% } %>

            <!--può essere gestito tramite un menù a discesa con 2 opzioni-->
            <select id="tipo" name="tipo">
                <option value="user">Utente</option>
                <option value="seller">Venditore</option>
            </select>

            <input type="password" name="password" placeholder="Password" required>


            <button type="submit">Registrati ora!</button>
            <% if (request.getAttribute("register-error") != null) { %>
            <div class="error-message"><%= request.getAttribute("register-error") %></div>
            <% } %>
        </form>
    </div>
</div>


</body>
</html>
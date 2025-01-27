package main.java.gestioneAccount.areaUtente;

import main.java.gestioneAccount.utente.UtenteBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AreaUtenteServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        UtenteBean bean = (UtenteBean) session.getAttribute("utente");

        if (bean == null){
            response.sendRedirect("/LoginServlet");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(getServletContext().getContextPath()+"/profiloUtente.jsp");
        dispatcher.forward(request, response);
    }
}

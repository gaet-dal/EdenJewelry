package main.java.presentation.areaRiservata;

import main.java.dataManagement.bean.UtenteBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AreaRiservataServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        UtenteBean bean = (UtenteBean) session.getAttribute("utente");

        if (bean == null) {
            response.sendRedirect("/LoginServlet");
        } else if (bean.getTipo().equals("user")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(getServletContext().getContextPath()+"/profiloUtente.jsp");
            dispatcher.forward(request, response);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(getServletContext().getContextPath() + "/ProfiloVenditore.jsp");
        dispatcher.forward(request, response);
    }
}

package main.java.presentation.acquisti;

import main.java.dataManagement.bean.UtenteBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OrdineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        HttpSession session = request.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        if(utente == null)
            response.sendRedirect("login.jsp");

        if(utente.getTipo().equals("seller")){
            RequestDispatcher dispatcher = request.getRequestDispatcher(getServletContext().getContextPath()+ "/ProfiloVenditore.jsp");
            dispatcher.forward(request, response);
        }else{
            RequestDispatcher dispatcher = request.getRequestDispatcher(getServletContext().getContextPath()+ "/profiloUtente.jsp");
            dispatcher.forward(request, response);
        }
    }
}

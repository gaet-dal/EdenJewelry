package main.java.gestioneAccount.areaRiservata;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.io.IOException;

import main.java.gestioneAccount.utente.UtenteDAO;
import main.java.gestioneAccount.utente.UtenteBean;

//@WebServlet("/UserProfileServlet")
public class profili_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UtenteDAO utenti;

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
        utenti = new UtenteDAO(ds);

        HttpSession session = request.getSession(false);
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/PaginaLogin.jsp");
        } else {
            String tipo = utente.getTipo(); //recuperiamo il tipo dell'utente
            //request.setAttribute("utente", utente);   settaggio inutile

            if(tipo=="user"){ //se l'utente è tipo base, mostriamo il profilo proposto da Profilo_utente.jsp
                request.getRequestDispatcher("ProfiloUser.jsp").forward(request, response);
            }
            else if(tipo=="seller") {//se l'utente è un admin, lo reidirizziamo a Profilo_admin.jsp, che ha delle funzionalità in più
                request.getRequestDispatcher("ProfiloSeller.jsp").forward(request, response);

            }
        }
    }

    public void destroy() {
        super.destroy();
        // Eventuale chiusura risorse
    }
}

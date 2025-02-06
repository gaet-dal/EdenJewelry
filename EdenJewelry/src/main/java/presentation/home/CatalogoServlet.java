package main.java.presentation.home;

import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;
import main.java.application.gestioneCatalogo.Catalogo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CatalogoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    //usiamo questa servlet per gestire la richiesta di un utente di poter effettuare il login al sito e, in base ai dati inseriti,
    //restituaiamo una visione di questo consona al suo tipo (cioè seller o user);

    private static Logger logger = Logger.getLogger(CatalogoServlet.class.getName());

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
    }

    private ProdottoDAO prodottoDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");
        Catalogo cat = new Catalogo(ds);

        HttpSession session=request.getSession();
        UtenteBean utente= (UtenteBean) session.getAttribute("utente"); //recuperare l'utente dalla sessione;

        //adesso recuperiamo il tipo dell'utente,
        //se è venditore, permettiamo di effettuare aggiunte e rimozioni dal catalogo;

        String tipo= utente.getTipo();
        if("user".equals(tipo)){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Non puoi accedere alle funzionalità del venditore");
        }

       String ac = request.getParameter("submitAction");

        if(ac.equals("Aggiungi")){
            String nome = request.getParameter("nome");
            float prezzo = Float.parseFloat(request.getParameter("prezzo"));
            int quantità = Integer.parseInt(request.getParameter("quantita"));
            String categoria = request.getParameter("categoria");
            //molto probabilmente questa parte del codice va modificata
            String immagine = request.getParameter("immagine");

            ProdottoBean bean = new ProdottoBean();
            bean.setNome(nome);
            bean.setPrezzo(prezzo);
            bean.setQuantita(quantità);
            bean.setCategoria(categoria);
            bean.setImmagine(immagine);

            try {
                if(cat.addProduct(bean))
                    logger.info("Aggiunta prodotto riuscita");
                else
                    logger.info("Aggiunta prodotto non riuscita");
            } catch (SQLException e) {
                logger.warning("Errore durante il salvataggio del prodotto nel database");
                e.printStackTrace();
            }
        } else if (ac.equals("Elimina")) {
            String nome = request.getParameter("id");
            ProdottoBean bean = new ProdottoBean();
            bean.setNome(nome);

            try{
                if(cat.removeProduct(bean))
                    logger.info("Rimozione prodotto riuscita");
                else
                    logger.info("Rimozione prodotto non riuscita");
            } catch (SQLException e) {
                logger.warning("Errore durante la rimozione del prodotto dal database");
                e.printStackTrace();
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher(getServletContext().getContextPath()+"/homepage.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

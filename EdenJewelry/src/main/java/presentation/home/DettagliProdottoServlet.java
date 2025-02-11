package main.java.presentation.home;

import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;

public class DettagliProdottoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) request.getServletContext().getAttribute("MyDataSource");
        ProdottoDAO prodottoDAO = new ProdottoDAO(ds);

        String nome = request.getParameter("nome");
        System.out.println("nome "+nome);

        if (nome != null) {

            try {
              ProdottoBean bean=prodottoDAO.doRetrieveByNome(nome);

              if(bean!=null) {
                  request.setAttribute("prodotto", bean);
                  RequestDispatcher dispatcher = request.getRequestDispatcher("/script/DettagliProdotto.jsp");
                  dispatcher.forward(request, response);
              }
              else System.out.println("Prodotto non trovato"); //stampa di controllo;

            } catch (SQLException e) {
                System.out.println("bean null");
                throw new RuntimeException(e);
            }

        }
        else System.out.println("Nome prodotto non trovato");

    }

}

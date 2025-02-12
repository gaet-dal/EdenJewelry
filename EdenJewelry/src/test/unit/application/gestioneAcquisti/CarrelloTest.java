package test.unit.application.gestioneAcquisti;

import main.java.application.gestioneAcquisti.Carrello;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CarrelloTest {
    @Mock ProdottoDAO prodottoDAO;

    @Mock HttpSession session;

    public Carrello carrello;

    @Before
    public void setUp() throws Exception {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
    carrello = new Carrello("test@gmail.com");
    }

    @Test
    public void testAddProdotto() throws Exception {
        ProdottoBean prodotto = new ProdottoBean();

    }

    @Test
    public void testRemoveProdotto() throws Exception {
        String email = "test@gmail.com";
    }

}

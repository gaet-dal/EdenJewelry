package test.unit.application.gestioneAcquisti;

import main.java.application.gestioneAcquisti.Carrello;
import main.java.application.gestioneAcquisti.CheckoutControl;
import main.java.application.gestioneAcquisti.ItemCarrello;
import main.java.dataManagement.bean.OrdineBean;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.bean.RigaOrdineBean;
import main.java.dataManagement.dao.OrdineDAO;
import main.java.dataManagement.dao.ProdottoDAO;
import main.java.dataManagement.dao.RigaOrdineDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CheckoutControlTest {
    @Mock
    DataSource ds;

    @Mock
    Carrello carrello;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    private OrdineDAO ordineDAO;
    private RigaOrdineDAO rigaOrdineDAO;
    private ProdottoDAO prodottoDAO;


    @BeforeEach
    public void setUp() {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        ordineDAO = new OrdineDAO(ds);
        rigaOrdineDAO = new RigaOrdineDAO(ds);
        prodottoDAO = new ProdottoDAO(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testCheckoutSuccess() throws SQLException { //DA FARE
        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(ordineDAO.doSave(any(OrdineBean.class))).thenReturn(true);

        boolean result = CheckoutControl.checkout(carrello, "email@example.com", "2222222222 22 22 222", "indirizzo 1", ds);

        assertTrue(result);

    }

    @Test
    public void testCheckoutEmptyCart() {
        when(carrello.isEmpty()).thenReturn(true);

        boolean result = CheckoutControl.checkout(carrello, "email@example.com", "2222222222 22 22 222", "indirizzo 1", ds);

        assertFalse(result);
    }


    @Test
    public void testCheckoutInvalidMetodoPagamento(){
        when(carrello.isEmpty()).thenReturn(false);

        boolean result = CheckoutControl.checkout(carrello, "email@example.com", "carta", "indirizzo 1", ds);

        assertFalse(result);
    }


    //funzione che restituisce un carrello con degli oggetti
    private List<ItemCarrello> getMockItems() {
        ItemCarrello item1 = new ItemCarrello("", 0);
        item1.setNome("Prodotto1");
        item1.setQuantità(2);

        ItemCarrello item2 = new ItemCarrello("", 0);
        item2.setNome("Prodotto2");
        item2.setQuantità(3);

        return Arrays.asList(item1, item2);
    }

}
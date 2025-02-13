package test.integration.application.gestioneAcquisti;

import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;
import main.java.application.gestioneAcquisti.Carrello;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CarrelloTest {

    @Mock
    private DataSource dataSourceMock;

    @Mock
    private Connection connectionMock;

    @Mock
    private PreparedStatement preparedStatementMock;

    @InjectMocks
    private ProdottoDAO prodottoDAO;

    private ProdottoBean prodotto;
    private Carrello carrello;

    @BeforeEach
    public void setUp() throws SQLException {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);

        when(dataSourceMock.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(preparedStatementMock);

        // Crea un prodotto per i test
        prodotto = new ProdottoBean();
        prodotto.setNome("Test Prodotto");
        prodotto.setPrezzo(10.0f);
        prodotto.setQuantita(5);
        prodotto.setCategoria("Test Categoria");
        prodotto.setImmagine("test.jpg");

        // Simula l'inserimento del prodotto nel database
        when(preparedStatementMock.executeUpdate()).thenReturn(1); // Simula un inserimento riuscito
        prodottoDAO.doSave(prodotto);

        // Inizializza il carrello
        carrello = new Carrello("test@example.com");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testAddToCart() {
        carrello.addToCart(prodotto.getNome());

        // Verifica che il prodotto sia stato aggiunto al carrello
        assertFalse(carrello.isEmpty());
        assertEquals(1, carrello.getListProdotti().size());
        assertEquals(prodotto.getNome(), carrello.getListProdotti().get(0).getNome());
    }

    @Test
    public void testDeleteFromCart() {
        carrello.addToCart(prodotto.getNome());
        carrello.deleteFromCart(prodotto.getNome());

        // Verifica che il carrello sia vuoto
        assertTrue(carrello.isEmpty());
    }

    @Test
    public void testModificaQuantità() {
        carrello.addToCart(prodotto.getNome());
        carrello.modificaQuantità(prodotto.getNome(), 3);

        // Verifica che la quantità sia stata modificata
        assertEquals(3, carrello.getListProdotti().get(0).getQuantità());
    }

    @Test
    public void testIsEmpty() {
        // verifica per carrello vuoto -inizialmente-
        assertTrue(carrello.isEmpty());

        // aggiungi un prodotto e verifica che non sia più vuoto
        carrello.addToCart(prodotto.getNome());
        assertFalse(carrello.isEmpty());
    }

    @Test
    public void testAddDuplicateProduct() {
        carrello.addToCart(prodotto.getNome());
        carrello.addToCart(prodotto.getNome());

        // Verifica che la quantità sia incrementata
        assertEquals(2, carrello.getListProdotti().get(0).getQuantità());
    }
}


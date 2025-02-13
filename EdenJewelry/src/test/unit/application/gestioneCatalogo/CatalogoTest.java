package test.unit.application.gestioneCatalogo;

import main.java.application.gestioneCatalogo.Catalogo;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CatalogoTest {
    @Mock
    DataSource ds;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    AutoCloseable autoCloseable;

    @Mock
    ProdottoDAO prodottoDAO;


    private Catalogo catalogo;

    @BeforeEach
    public void setUp() {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        catalogo = new Catalogo(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void addProductSuccess() throws SQLException {
        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setNome("Collana argento");
        prodotto.setPrezzo(20.0f);
        prodotto.setImmagine("collana.jpg");
        prodotto.setCategoria("collane");
        prodotto.setQuantita(2);

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        doNothing().when(preparedStatement).close(); // Chiudi PreparedStatement
        doNothing().when(connection).close();
        doNothing().when(prodottoDAO).closeResources(autoCloseable);

        when(prodottoDAO.doSave(prodotto)).thenReturn(true);

        boolean result = catalogo.addProduct(prodotto);
        assertTrue(result);
    }

    @Test
    public void addProductInvalidName() throws SQLException {
        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setNome("Collana argent0");
        prodotto.setPrezzo(20.0f);
        prodotto.setImmagine("collana.jpg");
        prodotto.setCategoria("collane");
        prodotto.setQuantita(2);

        boolean result = catalogo.addProduct(prodotto);
        assertFalse(result);
    }

    @Test
    public void addProductInvalidPrice() throws SQLException {
        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setNome("Collana argento");
        prodotto.setPrezzo(-1.0f);
        prodotto.setImmagine("collana.jpg");
        prodotto.setCategoria("collane");
        prodotto.setQuantita(2);

        boolean result = catalogo.addProduct(prodotto);
        assertFalse(result);
    }

    @Test
    public void addProductInvalidCategory() throws SQLException {
        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setNome("Collana argento");
        prodotto.setPrezzo(20.0f);
        prodotto.setImmagine("collana.jpg");
        prodotto.setCategoria("patate");
        prodotto.setQuantita(2);

        boolean result = catalogo.addProduct(prodotto);
        assertFalse(result);
    }

    @Test
    public void addProductInvalidQuantity() throws SQLException {
        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setNome("Collana argent0");
        prodotto.setPrezzo(20.0f);
        prodotto.setImmagine("collana.jpg");
        prodotto.setCategoria("collane");
        prodotto.setQuantita(0);

        boolean result = catalogo.addProduct(prodotto);
        assertFalse(result);
    }

    @Test
    public void removeProduct() throws SQLException {
        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setNome("Collana argento");

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        doNothing().when(preparedStatement).close(); // Chiudi PreparedStatement
        doNothing().when(connection).close();
        doNothing().when(prodottoDAO).closeResources(autoCloseable);

        when(prodottoDAO.doDelete(anyString())).thenReturn(true);

        boolean result = catalogo.removeProduct(prodotto);
        assertTrue(result);
    }
}
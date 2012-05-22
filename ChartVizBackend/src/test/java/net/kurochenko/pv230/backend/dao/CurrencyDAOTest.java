package net.kurochenko.pv230.backend.dao;

import net.kurochenko.pv230.backend.model.Currency;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public class CurrencyDAOTest extends AbstractTest {

    @Autowired
    private CurrencyDAO currencyDAO;

    private Currency currency;


    @Before
    public void setUp() {
        currency = new Currency();
        currency.setName("USD");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() {
        currencyDAO.create(null);
    }

    @Test
    public void testCreate() {
        currencyDAO.create(currency);

        assertNotNull(currency.getId());
        Currency retrieved = currencyDAO.find(currency.getId());
        assertEquals(currency, retrieved);
        assertEquals("USD", retrieved.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindIdNull() {
        currencyDAO.find(null);
    }

    @Test
    public void testFindId() {
        currencyDAO.create(currency);
        assertEquals(currency, currencyDAO.find(currency.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByNameNull() {
        currencyDAO.findByName(null);
    }

    @Test
    public void testFindByName() {
        currencyDAO.create(currency);
        assertEquals(currency, currencyDAO.findByName("USD"));
    }

    @Test
    public void testFindAll() {
        Currency currency2 = new Currency();
        currency2.setName("EUR");

        assertTrue(currencyDAO.findAll().isEmpty());

        currencyDAO.create(currency);
        currencyDAO.create(currency2);

        assertEquals(2, currencyDAO.findAll().size());
        assertTrue(currencyDAO.findAll().contains(currency));
        assertTrue(currencyDAO.findAll().contains(currency2));
    }


}

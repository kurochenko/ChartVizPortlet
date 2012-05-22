package net.kurochenko.pv230.backend.dao;

import net.kurochenko.pv230.backend.model.Currency;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public class CurrencyDAOTest extends AbstractTest {

    @Autowired
    private CurrencyDAO currencyDAO;

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() {
        currencyDAO.create(null);
    }

    @Test
    public void testCreate() {
        Currency currency = new Currency();
        currency.setName("USD");
        currencyDAO.create(currency);

        assertNotNull(currency.getId());
        Currency retrieved = currencyDAO.find(currency.getId());
        assertEquals(currency, retrieved);
        assertEquals("USD", retrieved.getName());
    }


}

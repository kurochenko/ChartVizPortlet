package net.kurochenko.pv230.backend.dao;

import net.kurochenko.pv230.backend.model.Currency;
import net.kurochenko.pv230.backend.model.CurrencyValue;

import java.util.Date;
import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public interface CurrencyValueDAO {
    void create(CurrencyValue cv);
    CurrencyValue find(Long id);
    CurrencyValue find(String name, Date date);
    List<CurrencyValue> findRange(Currency currency, Date from);
}

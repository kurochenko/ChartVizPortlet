package net.kurochenko.pv230.backend.dao;

import net.kurochenko.pv230.backend.model.Currency;

import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public interface CurrencyDAO {
    void create(Currency currency);
    void update(Currency currency);
    Currency find(Long id);
    Currency findByName(String name);
    List<Currency> findAll();
    List<Currency> findVisible();
    void clear();
    void setVisible(List<Currency> currencies);
    void unsetVisible();
}

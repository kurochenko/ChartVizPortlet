package net.kurochenko.pv230.backend.dao;

import net.kurochenko.pv230.backend.model.Currency;

import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public interface CurrencyDAO {
    void create(Currency currency);
    Currency find(Long id);
    Object find(String name);
    List<Currency> findAll();
}

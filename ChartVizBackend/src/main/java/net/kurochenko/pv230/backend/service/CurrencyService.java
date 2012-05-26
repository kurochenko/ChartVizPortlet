package net.kurochenko.pv230.backend.service;

import net.kurochenko.pv230.backend.model.ChartDTO;
import net.kurochenko.pv230.backend.model.Currency;
import net.kurochenko.pv230.backend.model.ExchangeRateDTO;

import java.util.Date;
import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public interface CurrencyService {

    void create(ExchangeRateDTO dto);
    void update(Currency currency);
    void updateAll(List<Currency> currencies);
    ChartDTO find(String currencyName, Date from);
    List<Currency> findAll();
    List<Currency> findVisible();
    Currency findByName(String name);
    void clear();

}

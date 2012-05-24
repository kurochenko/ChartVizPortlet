package net.kurochenko.pv230.backend.service;

import net.kurochenko.pv230.backend.model.CurrencyValue;
import net.kurochenko.pv230.backend.parser.ExchangeRateDTO;

import java.util.Date;
import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public interface CurrencyService {

    void create(ExchangeRateDTO dto);
    List<CurrencyValue> find(String currencyName, Date from);

}

package net.kurochenko.pv230.backend.service;

import net.kurochenko.pv230.backend.model.ChartDTO;
import net.kurochenko.pv230.backend.model.ExchangeRateDTO;

import java.util.Date;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public interface CurrencyService {

    void create(ExchangeRateDTO dto);
    ChartDTO find(String currencyName, Date from);
    void clear();

}

package net.kurochenko.pv230.backend.service;

import net.kurochenko.pv230.backend.dao.CurrencyDAO;
import net.kurochenko.pv230.backend.dao.CurrencyValueDAO;
import net.kurochenko.pv230.backend.model.ChartDTO;
import net.kurochenko.pv230.backend.model.Currency;
import net.kurochenko.pv230.backend.model.CurrencyValue;
import net.kurochenko.pv230.backend.model.ExchangeRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyDAO currencyDAO;

    @Autowired
    private CurrencyValueDAO currencyValueDAO;


    @Override
    public void create(ExchangeRateDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO is null");
        }

        for (Date date : dto.getRates().keySet()) {

            Map<String, BigDecimal> currencies = dto.getRates().get(date);
            for (String currencyName : currencies.keySet()) {

                Currency actualCurrency = currencyDAO.findByName(currencyName);
                if (actualCurrency == null) {
                    Currency c = new Currency();
                    c.setName(currencyName);
                    currencyDAO.create(c);
                } else if (currencyValueDAO.find(currencyName, date) == null) {
                    CurrencyValue cv = new CurrencyValue();
                    cv.setTime(date);
                    cv.setValue(currencies.get(currencyName));
                    cv.setCurrency(actualCurrency);

                    currencyValueDAO.create(cv);
                }
            }
        }
    }

    @Override
    public void update(Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency is null");
        }

        currencyDAO.update(currency);
    }

    @Override
    public void setVisible(List<Currency> currencies) {
        currencyDAO.unsetVisible();
        if (currencies != null) {
            currencyDAO.setVisible(currencies);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ChartDTO find(String currencyName, Date from) {
        if (currencyName == null) {
            throw new IllegalArgumentException("Currency is null");
        }
        if (from == null) {
            throw new IllegalArgumentException("From is null");
        }

        ChartDTO result = new ChartDTO();
        result.setCurrency(currencyDAO.findByName(currencyName));

        if (result.getCurrency() == null) {
            return null;
        }

        result.setValues(currencyValueDAO.findRange(result.getCurrency(), from));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Currency find(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is null");
        }

        return currencyDAO.find(id);
    }

    @Override
    public List<Currency> findAll() {
        return currencyDAO.findAll();
    }

    @Override
    public List<Currency> findVisible() {
        return currencyDAO.findVisible();
    }

    @Override
    public Currency findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name is null");
        }

        return currencyDAO.findByName(name);
    }

    @Override
    public void clear() {
        currencyValueDAO.clear();
        currencyDAO.clear();
    }
}

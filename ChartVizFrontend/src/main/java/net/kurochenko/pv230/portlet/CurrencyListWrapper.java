package net.kurochenko.pv230.portlet;

import net.kurochenko.pv230.backend.model.Currency;

import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public class CurrencyListWrapper {

    private List<Currency> currencies;


    public CurrencyListWrapper(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyListWrapper that = (CurrencyListWrapper) o;

        if (currencies != null ? !currencies.equals(that.currencies) : that.currencies != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return currencies != null ? currencies.hashCode() : 0;
    }
}

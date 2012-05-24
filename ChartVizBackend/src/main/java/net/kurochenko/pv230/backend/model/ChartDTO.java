package net.kurochenko.pv230.backend.model;

import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public class ChartDTO {

    private Currency currency;
    private List<CurrencyValue> values;


    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<CurrencyValue> getValues() {
        return values;
    }

    public void setValues(List<CurrencyValue> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChartDTO chartDTO = (ChartDTO) o;

        if (currency != null ? !currency.equals(chartDTO.currency) : chartDTO.currency != null) return false;
        if (values != null ? !values.equals(chartDTO.values) : chartDTO.values != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (values != null ? values.hashCode() : 0);
        return result;
    }
}

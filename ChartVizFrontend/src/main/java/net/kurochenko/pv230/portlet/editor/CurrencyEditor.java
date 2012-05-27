package net.kurochenko.pv230.portlet.editor;

import net.kurochenko.pv230.backend.model.Currency;
import net.kurochenko.pv230.backend.service.CurrencyService;

import java.beans.PropertyEditorSupport;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public class CurrencyEditor extends PropertyEditorSupport {

    private CurrencyService currencyService;


    public CurrencyEditor(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public String getAsText() {
        return (getValue() == null)
                ? ""
                : ((Currency) getValue()).getId().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.equals("")) {
            setValue(null);
        } else {
            setValue(currencyService.find(Long.valueOf(text)));
        }
    }
}

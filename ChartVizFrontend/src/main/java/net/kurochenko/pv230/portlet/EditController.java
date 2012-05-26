package net.kurochenko.pv230.portlet;

import net.kurochenko.pv230.backend.model.Config;
import net.kurochenko.pv230.backend.service.ConfigService;
import net.kurochenko.pv230.backend.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import static net.kurochenko.pv230.portlet.Constants.CONFIG_FORM_MODEL;
import static net.kurochenko.pv230.portlet.Constants.CONFIG_FORM_NAME;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Controller
@RequestMapping("EDIT")
public class EditController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private CurrencyService currencyService;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id", "lastTimeRange", "lastCurrency");
    }

    @ModelAttribute(CONFIG_FORM_MODEL)
    public Config getConfig() {
        return configService.load();
    }

//    @ModelAttribute(CURRENCY_FORM_MODEL)
//    public CurrencyListWrapper getCurrencies() {
//        return new CurrencyListWrapper(currencyService.findAll());
//    }

    @RenderMapping
    public String renderEdit() {
        return "chartviz/edit";
    }

    @ActionMapping(CONFIG_FORM_NAME)
    public void saveConfiguration(@ModelAttribute(CONFIG_FORM_MODEL) Config config) {
        configService.save(config);
    }

//    @ActionMapping(CURRENCY_FORM_NAME)
//    public void saveConfiguration(@ModelAttribute(CURRENCY_FORM_MODEL) CurrencyListWrapper wrapper) {
//        currencyService.updateAll(wrapper.getCurrencies());
//    }
}

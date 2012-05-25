package net.kurochenko.pv230.backend.schedule;

import net.kurochenko.pv230.backend.model.Config;
import net.kurochenko.pv230.backend.model.Currency;
import net.kurochenko.pv230.backend.parser.ExchangeRateParser;
import net.kurochenko.pv230.backend.service.ConfigService;
import net.kurochenko.pv230.backend.service.CurrencyService;
import net.kurochenko.pv230.backend.util.TimeRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Component
public class ParseScheduler {

    @Autowired
    private ExchangeRateParser parser;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ConfigService configService;


    @PostConstruct
    public void clear() {
        currencyService.clear();
    }

    @PostConstruct
    public void parse90DCurrencies() {
        currencyService.create(parser.parse90DaysOld());
    }

    @PostConstruct
    public void setVisibleCurrencies() {
        List<String> visible = new ArrayList<String>(){{
            add("CZK");
            add("USD");
        }};

        for (String cName : visible) {
            Currency c = currencyService.findByName(cName);
            c.setVisible(true);
            currencyService.update(c);
        }
    }

    @PostConstruct
    public void setConfig() {
        if (configService.load() == null) {
            Config c = new Config();
            c.setImgHeight(600);
            c.setImgWidth(800);
            c.setLastCurrency(currencyService.findByName("CZK"));
            c.setLastTimeRange(TimeRange.WEEK.getName());
            configService.save(c);
        }
    }

}

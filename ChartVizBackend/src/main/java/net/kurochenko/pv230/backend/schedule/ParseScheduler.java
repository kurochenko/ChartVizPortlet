package net.kurochenko.pv230.backend.schedule;

import net.kurochenko.pv230.backend.parser.ExchangeRateParser;
import net.kurochenko.pv230.backend.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Component
public class ParseScheduler {

    @Autowired
    private ExchangeRateParser parser;

    @Autowired
    private CurrencyService currencyService;


    @PostConstruct
    public void clear() {
        currencyService.clear();
    }

    @PostConstruct
    public void parse90DCurrencies() {
        currencyService.create(parser.parse90DaysOld());
    }

//    @PostConstruct
//    @Scheduled(cron = "0 5 0 * * *")
    public void parseAllCurrencies() {
        currencyService.create(parser.parseActual());
    }
}

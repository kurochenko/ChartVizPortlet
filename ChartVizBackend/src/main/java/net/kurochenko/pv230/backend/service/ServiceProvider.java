package net.kurochenko.pv230.backend.service;

import net.kurochenko.pv230.backend.parser.ExchangeRateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Component
public class ServiceProvider {

    public static Logger logger = Logger.getLogger(ServiceProvider.class.getName());

    public static ExchangeRateParser parser;

    @Autowired
    public static void setParser(ExchangeRateParser parser) {
        ServiceProvider.parser = parser;
    }

    public static int parse() {
        return parser.parseAll().getRates().size();
    }
}

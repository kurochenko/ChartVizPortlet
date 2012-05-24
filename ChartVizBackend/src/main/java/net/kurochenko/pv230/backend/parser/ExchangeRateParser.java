package net.kurochenko.pv230.backend.parser;

import net.kurochenko.pv230.backend.model.ExchangeRateDTO;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public interface ExchangeRateParser {
    /**
     * Parses all currency rates since 1999. This action can be slow slow
     * @return
     */
    public ExchangeRateDTO parseAll();

    /**
     * Parses all currency rates that ar not older than 90 days
     * @return
     */
    public ExchangeRateDTO parse90DaysOld();

    /**
     * Parses current currency rates
     * @return
     */
    public ExchangeRateDTO parseActual();
}


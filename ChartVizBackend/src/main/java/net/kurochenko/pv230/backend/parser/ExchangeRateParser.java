package net.kurochenko.pv230.backend.parser;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
public interface ExchangeRateParser {
    public ExchangeRateDTO parseAll();
    public ExchangeRateDTO parseActual();
}


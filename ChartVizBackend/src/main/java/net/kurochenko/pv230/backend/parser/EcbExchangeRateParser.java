package net.kurochenko.pv230.backend.parser;

import net.kurochenko.pv230.backend.model.ExchangeRateDTO;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.kurochenko.pv230.backend.parser.EcbNamespaceContext.NS_ECB;
import static net.kurochenko.pv230.backend.parser.EcbNamespaceContext.NS_GMS;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Component
public class EcbExchangeRateParser implements ExchangeRateParser{

//    public static Logger logger = Logger.getLogger(EcbExchangeRateParser.class);
    public static Logger logger = Logger.getLogger(EcbExchangeRateParser.class.getName());


    /** Node attribute name which contains currency rate validity date */
    public static final String XML_TIME_ATTR_NAME = "time";

    /** Node attribute name which contains currency code */
    public static final String XML_CURRENCY_ATTR_NAME = "currency";

    /** Node attribute name which contains currency rate */
    public static final String XML_RATE_ATTR_NAME = "rate";

    /** Currency rate validity date format */
    public static final String VALIDITY_DATE_FORMAT = "y-M-d";

    /**
     * URL path to XML file with currency rates
     */
    private static final String RATES_XML_DAILY = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private static final String RATES_XML_90D = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
    private static final String RATES_XML_ALL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist.xml";
    public static final String DATE_XPATH = "/"+NS_GMS+":Envelope/"+NS_ECB+":Cube/"+NS_ECB+":Cube";
    public static final String CURRENCY_XPATH = "./"+NS_ECB+":Cube";

    @Override
    public ExchangeRateDTO parseActual() {
        try {
            return rawParse(RATES_XML_DAILY);
        } catch (XPathExpressionException e) {
//            logger.error("Failed to evaluate XPath expression.", e);
            logger.log(Level.SEVERE, "Failed to evaluate XPath expression.", e);
        }
        return null;
    }

    @Override
    public ExchangeRateDTO parseAll() {
        try {
            return rawParse(RATES_XML_ALL);
        } catch (XPathExpressionException e) {
//            logger.error("Failed to evaluate XPath expression.", e);
            logger.log(Level.SEVERE, "Failed to evaluate XPath expression.", e);
        }
        return null;
    }

    @Override
    public ExchangeRateDTO parse90DaysOld() {
        try {
            return rawParse(RATES_XML_90D);
        } catch (XPathExpressionException e) {
//            logger.error("Failed to evaluate XPath expression.", e);
            logger.log(Level.SEVERE, "Failed to evaluate XPath expression.", e);
        }
        return null;
    }

    private ExchangeRateDTO rawParse(String xmlUrl) throws XPathExpressionException {

        ExchangeRateDTO result = new ExchangeRateDTO();
        Map<Date, Map<String, BigDecimal>> resultMap = new HashMap<Date, Map<String, BigDecimal>>();

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new EcbNamespaceContext());

        XPathExpression dateXPath =  xpath.compile(DATE_XPATH);
        XPathExpression currencyXPath = xpath.compile(CURRENCY_XPATH);

        NodeList dateNodes = (NodeList) dateXPath.evaluate(getDocument(xmlUrl), XPathConstants.NODESET);

        for (int i = 0; i < dateNodes.getLength(); i++) {
            Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
            String time = dateNodes.item(i).getAttributes().getNamedItem(XML_TIME_ATTR_NAME).getNodeValue();
            NodeList currencyNodes = (NodeList) currencyXPath.evaluate(dateNodes.item(i), XPathConstants.NODESET);

            for (int j = 0; j < currencyNodes.getLength(); j++) {
                rates.put(
                        currencyNodes.item(j).getAttributes().getNamedItem(XML_CURRENCY_ATTR_NAME).getNodeValue(),
                        new BigDecimal(currencyNodes.item(j).getAttributes().getNamedItem(XML_RATE_ATTR_NAME).getNodeValue())
                );
            }

            resultMap.put(parseValidityDate(time), rates);
        }

        result.setRates(resultMap);
        return result;
    }

    /**
     * Parses string date to {@code java.util.Date}
     * @param date string date representation
     * @return date or {@code null} when parse error occurs
     */
    private Date parseValidityDate(String date) {

        DateFormat dateFormat = new SimpleDateFormat(VALIDITY_DATE_FORMAT);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
//            logger.error("Failed to parse currency rate validity date", e);
            logger.log(Level.SEVERE, "Failed to parse currency rate validity date.", e);
        }

        return null;
    }

    /**
     * Loads XML file with actual currency rates from URL saved in configuration file
     * and parses it into {@code org.w3c.dom.Document}
     * @return parsed document or {@code null} when any error occurs
     */
    private Document getDocument(String xmlUrl) {

        Document document = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(xmlUrl);
            inputStream = url.openStream();

            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);

            DocumentBuilder builder = domFactory.newDocumentBuilder();
            document = builder.parse(inputStream);
        } catch (MalformedURLException e) {
//            logger.error("Failed to retrieve XML file with currency rates.", e);
            logger.log(Level.SEVERE, "Failed to retrieve XML file with currency rates.", e);
        } catch (IOException e) {
//            logger.error("Failed to open XML file with currency rates.", e);
            logger.log(Level.SEVERE, "Failed to open XML file with currency rates.", e);
        } catch (SAXException e) {
//            logger.error("Failed to parse XML file with currency rates.", e);
            logger.log(Level.SEVERE, "Failed to parse XML file with currency rates.", e);
        } catch (ParserConfigurationException e) {
//            logger.error("Failed to configure currency rates XML file parser.", e);
            logger.log(Level.SEVERE, "Failed to configure currency rates XML file parser.", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
//                    logger.error("Failed to close input stream fo currency rates XML file", e);
                    logger.log(Level.SEVERE, "Failed to close input stream fo currency rates XML file.", e);
                }
            }
        }

        return document;
    }
}

package net.kurochenko.pv230.portlet;

import net.kurochenko.pv230.backend.model.ChartDTO;
import net.kurochenko.pv230.backend.model.Config;
import net.kurochenko.pv230.backend.model.Currency;
import net.kurochenko.pv230.backend.service.ConfigService;
import net.kurochenko.pv230.backend.service.CurrencyService;
import net.kurochenko.pv230.backend.util.TimeRange;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.entity.StandardEntityCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import javax.portlet.ResourceResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import static net.kurochenko.pv230.portlet.Constants.*;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Controller
@RequestMapping("VIEW")
public class ViewController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ConfigService configService;


    @ModelAttribute("currencies")
    public List<Currency> getCurrencyList() {
        return currencyService.findVisible();
    }

    @ModelAttribute("config")
    public Config getConfig() {
        return configService.load();
    }

    @RenderMapping
    public String renderTimeRange(Model model,
                                  @RequestParam(value = TIME_RANGE_PARAM, required = false) String timeRange,
                                  @RequestParam(value = CURRENCY_VAL_PARAM, required = false) String currencyName)
            throws IOException {

        processParameters(currencyName, timeRange);
        currencyName = getConfig().getLastCurrency().getName();
        timeRange = getConfig().getLastTimeRange();
        model.addAttribute(IMAGEMAP_ATTR, extractImageMapToModel(timeRange, currencyName));

        return "chartviz/index";
    }

    private void processParameters(String currencyName, String timeRange) {
        Config config = configService.load();
        if (currencyName != null) {
            config.setLastCurrency(currencyService.findByName(currencyName));
        }
        if (timeRange != null) {
            config.setLastTimeRange(timeRange);
        }
        configService.save(config);
    }

    private String extractImageMapToModel(String timeRange, String currencyName) {
        FileOutputStream os = null;
        ChartRenderingInfo info = null;
        try {
            os = new FileOutputStream("smt.png");
            info = writeChart(currencyService.find(currencyName, convertTimeRange(timeRange)), os);
        } catch (IOException e) {
            // TODO log
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO log
                }
            }
        }

        return ChartUtilities.getImageMap(IMAGEMAP_NAME, info);
    }

    @ResourceMapping(PLOT_RESOURCE_VAL)
    public void renderPlot(@RequestParam(value = TIME_RANGE_PARAM, required = false) String timeRange,
                           @RequestParam(value = CURRENCY_VAL_PARAM, required = false) String currencyName,
                           ResourceResponse response) throws IOException {
        response.setContentType("image/png");
        processParameters(currencyName, timeRange);
        currencyName = getConfig().getLastCurrency().getName();
        timeRange = getConfig().getLastTimeRange();
        writeChart(currencyService.find(currencyName, convertTimeRange(timeRange)), response.getPortletOutputStream());

    }

    private Date convertTimeRange(String timeRange) {
        return (timeRange != null)
                    ? TimeRange.valueOf(timeRange).getFrom()
                    : TimeRange.valueOf(getConfig().getLastTimeRange()).getFrom(); // default
    }

    private ChartRenderingInfo writeChart(ChartDTO chartDTO, OutputStream os) {
        if (chartDTO == null) {
            throw new IllegalArgumentException("ChartDTO is null");
        }
        if (os == null) {
            throw new IllegalArgumentException("OutputStream is null");
        }

        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

        try {
            ChartUtilities.writeChartAsPNG(
                    os,
                    new ChartCreator().create(chartDTO),
                    getConfig().getImgWidth(),
                    getConfig().getImgHeight(),
                    info
            );
        } catch (IOException e) {
            e.printStackTrace(); // TODO log
        }

        return info;
    }
}

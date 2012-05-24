package net.kurochenko.pv230.portlet;

import net.kurochenko.pv230.backend.model.ChartDTO;
import net.kurochenko.pv230.backend.service.CurrencyService;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.entity.StandardEntityCollection;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import static net.kurochenko.pv230.portlet.ChartVizPortletConstants.TIME_RANGE_PARAM;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Controller
@RequestMapping("VIEW")
public class ChartVizPortlet {

    public static final int CHART_WIDTH = 800;
    public static final int CHART_HEIGHT = 500;


    @Autowired
    private CurrencyService currencyService;

    @RenderMapping
    public String renderDefault() {
        return "chartviz/index";
    }

    @RenderMapping(params = TIME_RANGE_PARAM)
    public String renderTimeRange(RenderRequest request, RenderResponse response, Model model) {
        model.addAttribute("plot", request.getParameter(TIME_RANGE_PARAM) + " " + TimeRange.valueOf(request.getParameter(TIME_RANGE_PARAM)).getFrom());
        return "chartviz/index";
    }

    @ModelAttribute("listSize")
    public int preparePortlet(@RequestParam(value = TIME_RANGE_PARAM, required = false) String timeRange) {
        Date date = (timeRange != null)
                ? TimeRange.valueOf(timeRange).getFrom()
                : DateTime.now().minusMonths(2).toDate();

        return currencyService.find("CZK", date).getValues().size();
    }

    @ResourceMapping("eurPlot")
    public void renderPlot(@RequestParam(value = TIME_RANGE_PARAM, required = false) String timeRange,
                           ResourceResponse response) throws IOException {
        response.setContentType("image/png");

        Date date = (timeRange != null)
                ? TimeRange.valueOf(timeRange).getFrom()
                : DateTime.now().minusMonths(2).toDate();
        writeChart(currencyService.find("CZK", date), response.getPortletOutputStream());

    }

    private ChartRenderingInfo writeChart(ChartDTO chartDTO, OutputStream os) {
        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

        try {
            ChartUtilities.writeChartAsPNG(
                    os,
                    new ChartCreator().create(chartDTO),
                    CHART_WIDTH,
                    CHART_HEIGHT,
                    info
            );
        } catch (IOException e) {
            e.printStackTrace(); // TODO log

        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace(); // TODO log
                }
            }
        }
        return info;
    }
}

package net.kurochenko.pv230.portlet;

import net.kurochenko.pv230.backend.service.CurrencyService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.Date;

import static net.kurochenko.pv230.portlet.ChartVizPortletConstants.TIME_RANGE_PARAM;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Controller
@RequestMapping("VIEW")
public class ChartVizPortlet {

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
    public int preparePortlet(RenderRequest request) {
        Date date = (request.getParameter(TIME_RANGE_PARAM) != null)
                ? TimeRange.valueOf(request.getParameter(TIME_RANGE_PARAM)).getFrom()
                : DateTime.now().minusMonths(2).toDate();

        return currencyService.find("CZK", date).size();
    }
}

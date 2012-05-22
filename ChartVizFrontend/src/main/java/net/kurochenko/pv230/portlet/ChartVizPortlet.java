package net.kurochenko.pv230.portlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import static net.kurochenko.pv230.portlet.ChartVizPortletConstants.TIME_RANGE_PARAM;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Controller
@RequestMapping("VIEW")
public class ChartVizPortlet {

    @RenderMapping
    public String renderDefault() {
        return "chartviz/index";
    }

    @RenderMapping(params = TIME_RANGE_PARAM)
    public String renderTimeRange(RenderRequest request, RenderResponse response, Model model) {
        model.addAttribute("plot", request.getParameter(TIME_RANGE_PARAM));
        return "chartviz/index";
    }
}

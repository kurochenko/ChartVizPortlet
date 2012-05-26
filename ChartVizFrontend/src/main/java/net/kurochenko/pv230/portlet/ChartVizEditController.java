package net.kurochenko.pv230.portlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * @author Andrej Kuročenko <andrej@kurochenko.net>
 */
@Controller
@RequestMapping("EDIT")
public class ChartVizEditController {

    @RenderMapping
    public String renderEdit() {
        return "chartviz/edit";
    }

}

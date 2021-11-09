package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 * <p>
 * Dit is wat het programma doet
 */

@Controller
public class ForbiddenController {

    @GetMapping("/403")
    protected String showForbidden(Model model) {
        return "forbiddenPage";
    }
}

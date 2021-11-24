package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Controls the 403 page.
 */

@Controller
public class ForbiddenController {

    @GetMapping("/403")
    protected String showForbidden(Model model) {
        return "forbiddenPage";
    }
}

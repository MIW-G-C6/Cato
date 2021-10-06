package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls the homepage.
 */

@Controller
public class HomepageController {

    @GetMapping("/")
    protected String showHomepage() {
        return "homepage";
    }
}

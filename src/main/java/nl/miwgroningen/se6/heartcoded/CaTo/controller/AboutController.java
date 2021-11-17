package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 *
 * Controls the about page.
 */

@Controller
public class AboutController {

    @GetMapping("/about")
    protected String showAboutPage() {
        return "aboutPage";
    }
}

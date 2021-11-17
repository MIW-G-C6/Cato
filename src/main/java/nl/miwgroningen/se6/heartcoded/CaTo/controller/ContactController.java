package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls the contact page.
 */

@Controller
public class ContactController {

    @GetMapping("/contact")
    protected String showContactPage() {
        return "contactPage";
    }
}

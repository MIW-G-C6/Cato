package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserLoginDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls the homepage.
 */

@Controller
public class HomepageController {

    private UserService userService;

    public HomepageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    protected String showHomepage(Model model) {
        model.addAttribute("user", new UserLoginDTO());
        return "homepage";
    }

    @GetMapping("/loginRedirect")
    protected String redirectAfterSuccessfulLogin() {
        if (userService.currentUserIsSiteAdmin()) {
            return "redirect:/siteAdmin/dashboard";
        }
        return "redirect:/circles";
    }
}

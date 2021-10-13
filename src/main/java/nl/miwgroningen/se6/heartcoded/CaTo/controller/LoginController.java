package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls the login page.
 */

@Controller
public class LoginController {

    private UserService userService;

    @GetMapping("/login")
    protected String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
}

package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserLoginDTO;
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

    @GetMapping("/login")
    protected String showLoginPage(Model model) {
        model.addAttribute("user", new UserLoginDTO());
        return "login";
    }
}

package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls a registered user's profile page.
 */

@Controller
public class ProfilePageController {

    private UserService userService;

    public ProfilePageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profilepage/{userId}")
    protected String showProfilePage(@PathVariable("userId") Integer userId, Model model) {
        UserDTO userDTO = userService.getById(userId);
        model.addAttribute("user", userDTO);
        return "profilePage";
    }
}

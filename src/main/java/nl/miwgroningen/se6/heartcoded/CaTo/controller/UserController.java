package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls the user pages.
 */

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    protected String showUserOverview(Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        return "userOverview";
    }

    @GetMapping("/users/new")
    protected String showUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allUsers", userService.findAllUsers());
        return "userForm";
    }

    @PostMapping("/users/new")
    protected String saveOrUpdateUser(@ModelAttribute("user") User user, BindingResult result) {
        if(!result.hasErrors()) {
            userService.saveUser(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{userId}")
    protected String deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUserById(userId);
        return "redirect:/users";
    }
}

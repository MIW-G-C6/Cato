package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    protected String showUserOverview(Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        return "userOverview";
    }

    @GetMapping("/registration")
    protected String showUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allUsers", userService.findAllUsers());
        return "registrationForm";
    }

    @PostMapping("/registration")
    protected String saveUser(@ModelAttribute("user") User user, BindingResult result) {
        if(result.hasErrors()) {
            return "registrationForm";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/registration";
    }

    @GetMapping("/users/delete/{userId}")
    protected String deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUserById(userId);
        return "redirect:/users";
    }

    @GetMapping("/users/update/{userId}")
    protected String showUpdateUserForm(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.getById(userId);
        model.addAttribute("user", user);
        model.addAttribute("allUsers", userService.findAllUsers());
        return "editUserForm";
    }
}

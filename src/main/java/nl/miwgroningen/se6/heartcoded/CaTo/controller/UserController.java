package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserRegistrationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    @GetMapping("/registration")
    protected String showUserForm(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        model.addAttribute("allUsers", userService.findAllUsers());
        return "registrationForm";
    }

    @PostMapping("/registration")
    protected String doSaveOrEditUser(@ModelAttribute("user") UserDTO user,
                                      @ModelAttribute("userWithPW") UserRegistrationDTO userRegistrationDTO,
                                      BindingResult result) {
        String returnString;
        if (isUserAnonymous()) {
            returnString = saveNewUser(userRegistrationDTO, result);
        }
        else {
            returnString = editUser(user, result);
        }
        return returnString;
    }

    @GetMapping("/users/delete/{userId}")
    protected String deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUserById(userId);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{userId}")
    protected String showEditUserForm(@PathVariable("userId") Integer userId, Model model) {
        UserDTO user = userService.getById(userId);
        model.addAttribute("user", user);
        model.addAttribute("allUsers", userService.findAllUsers());
        return "editUserForm";
    }

    protected String saveNewUser(UserRegistrationDTO userRegistrationDTO, BindingResult result) {
        if (userService.emailInUse(userRegistrationDTO.getEmail())) {
            ObjectError error = new ObjectError("globalError", "Email is already in use");
            result.addError(error);
        }
        if (result.hasErrors()) {
            return "registrationForm";
        }
        userService.saveNewUser(userRegistrationDTO);
        return "redirect:/groups";
    }

    protected String editUser(UserDTO user, BindingResult result) {
        UserDTO currentUser = userService.getCurrentUser();
        if (userService.emailInUse(user.getEmail()) && !user.getEmail().equals(currentUser.getEmail())) {
            ObjectError error = new ObjectError("globalError", "Email is already in use");
            result.addError(error);
        }
        if (result.hasErrors()) {
            return "editUserForm"; //TODO Error is not shown in html page for some reason
        }
        userService.editUser(user);
        return "redirect:/profilepage/" + user.getUserId();
    }

    protected boolean isUserAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser");
    }
}

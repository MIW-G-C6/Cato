package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserRegistrationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskListService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    protected String showUserForm(Model model) {
        model.addAttribute("userWithPW", new UserRegistrationDTO());
        model.addAttribute("allUsers", userService.findAllUsers());
        return "registrationForm";
    }

    @GetMapping("/users/delete/{userId}")
    protected String deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUserById(userId);
        return "redirect:/siteAdminDashboard";
    }

    @GetMapping("/users/edit/{userId}")
    protected String showEditUserForm(@PathVariable("userId") Integer userId, Model model) {
        UserDTO user = userService.getById(userId);
        model.addAttribute("user", user);
        return "editUserForm";
    }

    @PostMapping("/registration")
    protected String saveNewUser(@ModelAttribute("userWithPW") UserRegistrationDTO userRegistrationDTO,
                                 BindingResult result) {
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

    @PostMapping("users/edit/{userId}")
    protected String editUser(@ModelAttribute("user") UserDTO user,
                              BindingResult result) {
        UserDTO currentUser = userService.getCurrentUser();
        if (userService.emailInUse(user.getEmail()) && !user.getEmail().equals(currentUser.getEmail())) {
            ObjectError error = new ObjectError("globalError", "Email is already in use");
            result.addError(error);
        }
        if (result.hasErrors()) {
            return "editUserForm";
        }
        userService.editUser(user);
        return "redirect:/profilepage/" + user.getUserId();
    }
}

package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserEditDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserRegistrationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls the user pages.
 */

@Controller
public class UserController {

    private static final String USER_IS_THE_LAST_ADMIN_IN_A_GROUP = "User is the last admin in a group";
    private static final String SITE_ADMIN_DELETE_ERROR = "Unable to delete site admin";

    private UserService userService;
    private MemberService memberService;

    public UserController(UserService userService, MemberService memberService) {
        this.userService = userService;
        this.memberService = memberService;
    }

    @GetMapping("/registration")
    protected String showUserForm(Model model) {
        model.addAttribute("userWithPW", new UserRegistrationDTO());
        model.addAttribute("allUsers", userService.findAllUsers());
        return "registrationForm";
    }

    @GetMapping("/users/delete/{userId}")
    protected String deleteUser(@PathVariable("userId") Integer userId, RedirectAttributes redirectAttributes) {
        if (!userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        if (memberService.userIsLastGroupAdminInAnyGroup(userId)) {
            redirectAttributes.addAttribute("error", USER_IS_THE_LAST_ADMIN_IN_A_GROUP);
        } else if (userService.userIsSiteAdmin(userId)) {
            redirectAttributes.addAttribute("error", SITE_ADMIN_DELETE_ERROR);
        } else {
            userService.deleteUserById(userId);
        }
        return "redirect:/siteAdminDashboard";
    }

    @GetMapping("/users/edit/{userId}")
    protected String showEditUserForm(@PathVariable("userId") Integer userId, Model model) {
        if (!userService.getCurrentUser().getUserId().equals(userId)) {
            return "redirect:/403";
        }
        UserEditDTO user = userService.getUserEditDTOById(userId);
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
    protected String editUser(@ModelAttribute("user") UserEditDTO user,
                              BindingResult result) {
        UserDTO currentUser = userService.getCurrentUser();
        if (!userService.getCurrentUser().getUserId().equals(user.getUserId()) ||
                userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        if (userService.emailInUse(user.getEmail()) && !user.getEmail().equals(currentUser.getEmail())) {
            ObjectError error = new ObjectError("globalError", "Email is already in use");
            result.addError(error);
        }
        if (!userService.passwordMatches(user.getOldPassword(), user.getUserId())) {
            ObjectError error = new ObjectError("globalError", "Incorrect password");
            result.addError(error);
        }
        if (!user.getNewPassword().equals(user.getConfirmNewPassword())) {
            ObjectError error = new ObjectError("globalError", "Passwords do not match");
            result.addError(error);
        }
        if (result.hasErrors()) {
            return "editUserForm";
        }
        userService.editUser(user);
        return "redirect:/profilepage/" + user.getUserId();
    }
}

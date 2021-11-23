package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserEditPasswordDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserRegistrationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Base64;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls the user pages.
 */

@Controller
public class UserController {

    private static final String USER_IS_THE_LAST_ADMIN_IN_A_CIRCLE = "User is the last admin in a care circle";
    private static final String SITE_ADMIN_DELETE_ERROR = "Unable to delete site admin";
    private static final int ONE_MEGABYTE = 1048576;

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

        if (memberService.userIsLastCircleAdminInAnyCircle(userId)) {
            redirectAttributes.addAttribute("error", USER_IS_THE_LAST_ADMIN_IN_A_CIRCLE);
        } else if (userService.userIsSiteAdmin(userId)) {
            redirectAttributes.addAttribute("error", SITE_ADMIN_DELETE_ERROR);
        } else {
            userService.deleteUserById(userId);
        }

        return "redirect:/siteAdmin/userOverview";
    }

    @GetMapping("/users/edit/{userId}")
    protected String showEditUserForm(@PathVariable("userId") Integer userId, @ModelAttribute("error") String error,
                                      Model model, HttpSession session) {
        if (!userService.getCurrentUser().getUserId().equals(userId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        session.setAttribute("editCircle", false);
        session.setAttribute("editUser", true);
        session.setAttribute("lastUserId", userId);

        UserDTO user = userService.getById(userId);

        model.addAttribute("userIsCurrentUser", userService.getCurrentUser().getUserId().equals(userId));
        model.addAttribute("currentProfilePicture", Base64.getEncoder().encodeToString(user.getProfilePicture()));
//        Base64.getEncoder().encodeToString(user.getProfilePicture())
        model.addAttribute("user", user);
        return "editUserForm";
    }

    @GetMapping("/users/edit/password/{userId}")
    protected String showEditPasswordForm(@PathVariable("userId") Integer userId, Model model) {
        if (!userService.getCurrentUser().getUserId().equals(userId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        UserEditPasswordDTO user = userService.getUserEditDTOById(userId);

        model.addAttribute("userIsCurrentUser", userService.getCurrentUser().getUserId().equals(userId));
        model.addAttribute("user", user);
        return "editPasswordForm";
    }

    @GetMapping("/users/edit/{userId}/deleteProfilePicture")
    protected String deleteProfilePicture(@PathVariable("userId") Integer userId) {
        if (!userService.getCurrentUser().getUserId().equals(userId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        userService.deleteProfilePicture(userId);

        return "redirect:/users/edit/" + userId;
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
        return "redirect:/circles";
    }

    @PostMapping("users/edit/{userId}")
    protected String editUser(@ModelAttribute("user") UserDTO user, @ModelAttribute("image") MultipartFile image,
                              BindingResult result) {
        try {
            byte[] imageContent = image.getBytes();
            if (image.getSize() < ONE_MEGABYTE) {
                user.setProfilePicture(imageContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!userService.getCurrentUser().getUserId().equals(user.getUserId())
                && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        if (userService.emailInUse(user.getEmail())
                && !user.getEmail().equals(userService.getById(user.getUserId()).getEmail())) {
            ObjectError error = new ObjectError("globalError", "Email is already in use");
            result.addError(error);
        }

        if (result.hasErrors()) {
            return "editUserForm";
        }

        userService.editUserInfo(user);
        return "redirect:/profilepage/" + user.getUserId();
    }

    @PostMapping("users/edit/password/{userId}")
    protected String editUserPassword(@ModelAttribute("user") UserEditPasswordDTO user,
                              BindingResult result, Model model) {
        if (!userService.getCurrentUser().getUserId().equals(user.getUserId())
                && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        if (!userService.currentUserIsSiteAdmin()) {
            if (!userService.passwordMatches(user.getOldPassword(), user.getUserId())) {
                ObjectError error = new ObjectError("globalError", "Incorrect password");
                result.addError(error);
            }
        }

        if (!user.getNewPassword().equals(user.getConfirmNewPassword())) {
            ObjectError error = new ObjectError("globalError", "Passwords do not match");
            result.addError(error);
        }

        if (result.hasErrors()) {
            model.addAttribute("userIsCurrentUser",
                    userService.getCurrentUser().getUserId().equals(user.getUserId()));
            return "editPasswordForm";
        }

        userService.editPassword(user);
        return "redirect:/profilepage/" + user.getUserId();
    }
}

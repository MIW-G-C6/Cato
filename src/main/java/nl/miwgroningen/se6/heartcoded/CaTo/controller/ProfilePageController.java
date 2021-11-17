package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Base64;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls a registered user's profile page.
 */

@Controller
public class ProfilePageController {

    private UserService userService;
    private MemberService memberService;

    public ProfilePageController(UserService userService, MemberService memberService) {
        this.userService = userService;
        this.memberService = memberService;
    }

    @GetMapping("/profilepage/{userId}")
    protected String showProfilePage(@PathVariable("userId") Integer userId, Model model) {
        UserDTO userDTO = userService.getById(userId);

        model.addAttribute("user", userDTO);
        if (userDTO.getProfilePicture() == null) {
            model.addAttribute("profilePicture", "");
        } else {
            model.addAttribute("profilePicture", Base64.getEncoder().encodeToString(userDTO.getProfilePicture()));
        }
        model.addAttribute("userIsCurrentUser", userService.getCurrentUser().getUserId().equals(userId));
        model.addAttribute("currentUserIsSiteAdmin", userService.currentUserIsSiteAdmin());
        model.addAttribute("allCircles", memberService.allCirclesByUserIdWithAdminCheck(userId));
        return "profilePage";
    }
}

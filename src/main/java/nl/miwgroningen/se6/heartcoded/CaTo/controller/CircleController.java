package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.CircleService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskListService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls the circle overview page.
 */

@Controller
public class CircleController {

    private CircleService circleService;
    private MemberService memberService;
    private UserService userService;
    private TaskListService taskListService;

    public CircleController(CircleService circleService, MemberService memberService, UserService userService,
                            TaskListService taskListService) {
        this.circleService = circleService;
        this.memberService = memberService;
        this.userService = userService;
        this.taskListService = taskListService;
    }

    @GetMapping("/circles")
    protected String showCircleOverview(HttpSession session, Model model) {
        memberService.checkForCircleDeletion();

        Integer currentUser = userService.getCurrentUser().getUserId();

        session.setAttribute("navbarCircles", memberService.getAllCirclesByUserId(currentUser));
        session.setAttribute("currentUserId", userService.getCurrentUser().getUserId());

        model.addAttribute("clientsCircleOne",
                memberService.findAllClientsInCircle(userService.getCircleOne(currentUser)));
        model.addAttribute("clientsCircleTwo",
                memberService.findAllClientsInCircle(userService.getCircleTwo(currentUser)));
        model.addAttribute("clientsCircleThree",
                memberService.findAllClientsInCircle(userService.getCircleThree(currentUser)));
        model.addAttribute("lastThreeCircles", memberService.getLastThreeCirclesByUserId(currentUser));
        model.addAttribute("allCircles", memberService.allCirclesByUserIdWithAdminCheck(currentUser));
        return "circleOverview";
    }

    @GetMapping("/circles/new")
    protected String showCircleForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("circle", new CircleDTO());
        model.addAttribute("allCircles", circleService.findAllCircles());
        return "circleForm";
    }

    @GetMapping("/circles/delete/{circleId}")
    protected String deleteCircleById(@PathVariable("circleId") Integer circleId, HttpSession session) {
        Integer currentUserId = userService.getCurrentUser().getUserId();

        if (!memberService.userIsCircleAdmin(circleId, currentUserId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        String circleDeleteRedirect = (String)session.getAttribute("circleDeleteRedirect");

        taskListService.deleteByCircleId(circleId);
        circleService.deleteCircleById(circleId);
        return circleDeleteRedirect;
    }

    @PostMapping("/circles/new")
    protected String saveOrUpdateCircle(@ModelAttribute("circle") CircleDTO circle,
                                        @ModelAttribute("member") MemberDTO member, BindingResult result) {
        if (!result.hasErrors()) {
            circleService.saveCircle(circle);

            UserDTO userDTO = userService.getCurrentUser();
            memberService.saveMember(new MemberDTO(userDTO.getUserId(), userDTO.getName(), circle.getCircleId(),
                    "Caregiver", true));

            taskListService.saveNew(circle);
        }
        return "redirect:/circles/" + circle.getCircleId();
    }
}
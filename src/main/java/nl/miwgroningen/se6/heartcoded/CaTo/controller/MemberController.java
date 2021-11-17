package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Controls the circleDashboard page
 */

@Controller
@RequestMapping("/circles")
public class MemberController {

    private static final String ONLY_CIRCLE_ADMIN_ERROR = "Cannot remove the last circle admin";
    private static final String NO_ACCOUNT_WITH_EMAIL_ERROR = "No existing account found with this email address";
    private static final String SAME_USER_TWICE_ERROR = "Not able to add the same user twice";
    private static final String ALREADY_CLIENT_ERROR = "User is already a client in another circle";

    private CircleService circleService;
    private MemberService memberService;
    private UserService userService;
    private TaskListService taskListService;
    private TaskService taskService;

    public MemberController(CircleService circleService, MemberService memberService,
                            UserService userService, TaskListService taskListService, TaskService taskService) {
        this.circleService = circleService;
        this.memberService = memberService;
        this.userService = userService;
        this.taskListService = taskListService;
        this.taskService = taskService;
    }

    @GetMapping("/{circleId}")
    protected String showCircleDashboard(@PathVariable("circleId") Integer circleId, Model model, HttpSession session) {
        if (!isCircleMember(circleId) && isNotSiteAdmin()) {
            return "redirect:/403";
        }
        memberService.addCircleToLastThreeCircles(circleId);
        Integer currentUser = userService.getCurrentUser().getUserId();
        session.setAttribute("navbarCircles", memberService.getAllCirclesByUserId(currentUser));
        model.addAttribute("thisCircle", circleService.getById(circleId));
        model.addAttribute("taskListId", taskListService.getByCircleId(circleId).getTaskListId());
        model.addAttribute("taskList", taskService.getAllTasksByCircleId(circleId));
        model.addAttribute("allCaregivers", memberService.findAllCaregiversByCircleId(circleId));
        model.addAttribute("thisUserIsAdmin", memberService.userIsCircleAdmin(circleId));
        model.addAttribute("allClients", memberService.findAllClientsInCircle(circleId));
        model.addAttribute("currentUserIsSiteAdmin", userService.currentUserIsSiteAdmin());
        return "circleDashboard";
    }

    @GetMapping("/options/{circleId}")
    protected String showCircleOptions(@PathVariable("circleId") Integer circleId,
                                      @ModelAttribute("error") String error, Model model,
                                       HttpSession session) {
        if (isNotCircleAdmin(circleId) && isNotSiteAdmin()) {
            return "redirect:/403";
        }
        session.setAttribute("circleDeleteRedirect", "redirect:/circles");
        model.addAttribute("thisCircle", circleService.getById(circleId));
        model.addAttribute("allMembersByCircleId", memberService.getAllByCircleId(circleId));
        return "circleOptions";
    }

    @GetMapping("/options/edit/{circleId}")
    protected String showCircleEdit(@PathVariable("circleId") Integer circleId, Model model) {
        if (isNotCircleAdmin(circleId) && isNotSiteAdmin()) {
            return "redirect:/403";
        }
        model.addAttribute("thisCircle", circleService.getById(circleId));
        return "circleEdit";
    }

    @PostMapping("/options/edit/{circleId}")
    protected String updateCircle(@ModelAttribute("circle") CircleDTO circle, BindingResult result) {
        if (isNotCircleAdmin(circle.getCircleId()) && isNotSiteAdmin()) {
            return "redirect:/403";
        }
        if (!result.hasErrors()) {
            circleService.saveCircle(circle);
        }
        return "redirect:/circles/options/{circleId}";
    }

    @RequestMapping(value = "/options/{circleId}/addmember")
    protected String doAddUser(@PathVariable("circleId") Integer circleId, Model model, String email,
                               @ModelAttribute ("addMember") MemberDTO addMember,
                               BindingResult result) {
        if (isNotCircleAdmin(circleId) && isNotSiteAdmin()) {
            return "redirect:/403";
        }
        if (email != null) {
            Optional<Integer> userId = userService.findUserIdByEmail(email);

            checkIfAccountExists(circleId, addMember, result, userId);

            if (!result.hasErrors()) {
                memberService.saveMember(addMember);
                return "redirect:/circles/options/{circleId}";
            }
        }

        model.addAttribute("circleUserRole", new MemberDTO());
        model.addAttribute("thisCircle", circleService.getById(circleId));
        model.addAttribute("member", memberService.getAllByCircleId(circleId));
        return "circleAddMember";
    }

    @GetMapping("/options/{circleId}/updatemember/{userId}")
    protected String showCircleUpdateMember(@PathVariable("userId") Integer userId,
                                            @PathVariable("circleId") Integer circleId, Model model) {

        if (isNotCircleAdmin(circleId) && isNotSiteAdmin()) {
            return "redirect:/403";
        }
        Optional<MemberDTO> member = memberService.findByUserIdAndCircleId(userId, circleId);
        if (member.isEmpty()) {
            return "redirect:/circles/options/{circleId}";
        }
        model.addAttribute("circle", circleService.getById(circleId));
        model.addAttribute("member", member.get());
        return "circleUpdateMember";
    }

    @PostMapping("/options/{circleId}/updatemember/{userId}")
    protected String updateCircleMember(@PathVariable("circleId") Integer circleId,
                                        @ModelAttribute("member") MemberDTO member,
                                        BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (isNotCircleAdmin(circleId) && isNotSiteAdmin()) {
            return "redirect:/403";
        }
        checkForErrorsUpdateCircleMember(circleId, member, result);
        if (result.hasErrors()) {
            model.addAttribute("circle", circleService.getById(circleId));
            model.addAttribute("member", member);
            return "circleUpdateMember";
        }
        if (isNotSiteAdmin() && circleAdminRemoveOwnRights(member)) {
            redirectAttributes.addFlashAttribute("member", member);
            return "redirect:/circles/options/{circleId}/updatemember/{userId}/changeAdminRole";
        }
        memberService.saveMember(member);
        if (!member.isAdmin() && member.getUserId().equals(userService.getCurrentUser().getUserId())) {
            return "redirect:/circles/{circleId}";
        }
        return "redirect:/circles/options/{circleId}";
    }

    @GetMapping("/options/{circleId}/updatemember/{userId}/changeAdminRole")
    protected String changeAdminRoleWarning(@PathVariable("circleId") Integer circleId,
                                            @PathVariable("userId") Integer userId,
                                            @ModelAttribute("member") MemberDTO member) {
        return "circleUpdateMemberWarning";
    }

    @PostMapping("/options/{circleId}/updatemember/{userId}/changeAdminRole")
    protected String updateChangeAdminRoleWarning(@PathVariable("circleId") Integer circleId,
                                                  @PathVariable("userId") Integer userId,
                                                  @ModelAttribute("member") MemberDTO member) {
        memberService.saveMember(member);
        return "redirect:/circles/" + circleId;
    }


    @GetMapping("/options/{circleId}/delete/{userId}")
    protected String deleteUserFromCircle(@PathVariable("circleId") Integer circleId,
                                          @PathVariable("userId") Integer userId,
                                          RedirectAttributes redirectAttributes) {

        boolean currentUserIsTargetUser = userId.equals(userService.getCurrentUser().getUserId());

        if (isNotCircleAdmin(circleId) && isNotSiteAdmin() && (!currentUserIsTargetUser)) {
            return "redirect:/403";
        }
        // checks first if the circle member isn't the last circle admin. If so, it can't be removed from the circle.
        Optional<MemberDTO> member = memberService.findByUserIdAndCircleId(userId, circleId);
        if (member.isPresent()) {
            if (isLastAdminInCircle(member.get())) {
                redirectAttributes.addAttribute("error", ONLY_CIRCLE_ADMIN_ERROR);
            } else {
                memberService.deleteByUserId(userId, circleId);
            }
        }

        if (currentUserIsTargetUser && !redirectAttributes.containsAttribute("error")) {
            return "redirect:/circles";
        }

        return "redirect:/circles/options/{circleId}";
    }

    private void checkIfAccountExists(Integer circleId, MemberDTO addMember, BindingResult result,
                                      Optional<Integer> userId) {
        if (!userId.isEmpty()) {
            addMember.setCircleId(circleId);
            addMember.setUserId(userId.get());;
            checkForErrorsAddMember(circleId, addMember, result, userId);
        } else {
            result.addError(new ObjectError("globalError", NO_ACCOUNT_WITH_EMAIL_ERROR));
        }
    }

    private void checkForErrorsAddMember(Integer circleId, MemberDTO makeMember, BindingResult result,
                                         Optional<Integer> userId) {
        if (memberService.userInCircleExists(makeMember)) {
            result.addError(new ObjectError("globalError", SAME_USER_TWICE_ERROR));
        }

        if (isAlreadyClient(circleId, makeMember, userId.get())) {
            result.addError(new ObjectError("globalError", ALREADY_CLIENT_ERROR));
        }
    }

    private void checkForErrorsUpdateCircleMember(Integer circleId, MemberDTO member, BindingResult result) {
        if (isAlreadyClient(circleId, member, member.getUserId())) {
            result.addError(new ObjectError("globalError", ALREADY_CLIENT_ERROR));
        }

        if (isLastAdminInCircle(member) && !member.isAdmin()) {
            result.addError(new ObjectError("globalError", ONLY_CIRCLE_ADMIN_ERROR));
        }
    }

    private boolean isAlreadyClient(Integer circleId, MemberDTO member, Integer userId) {
        return memberService.isClient(member) && memberService.isClientInOtherCircle(userId, circleId);
    }

    private boolean isLastAdminInCircle(MemberDTO member) {
        return memberService.findOutIfMemberIsAdmin(member) &&
                memberService.getCircleAdminsByCircleId(member.getCircleId()).size() == 1;
    }

    private boolean isCircleMember(Integer circleId) {
        return memberService.userIsMemberOfCircle(circleId);
    }

    private boolean isNotCircleAdmin(Integer circleId) {
        return !memberService.userIsCircleAdmin(circleId);
    }

    private boolean isNotSiteAdmin() {
        return !userService.currentUserIsSiteAdmin();
    }

    private boolean circleAdminRemoveOwnRights(MemberDTO memberDTO) {
        Integer currentUserId = userService.getCurrentUser().getUserId();
        if (!memberDTO.isAdmin()
                && memberService.findOutIfMemberIsAdmin(
                        memberService.findByUserIdAndCircleId(currentUserId , memberDTO.getCircleId()).get())
                && memberDTO.getUserId().equals(currentUserId)) {
            return true;
        }
        return false;
    }
}
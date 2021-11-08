package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Controls the groupDashboard page
 */

@Controller
@RequestMapping("/groups")
public class MemberController {

    private static final String ONLY_GROUP_ADMIN_ERROR = "Cannot remove group admin if this is the only group admin";
    private static final String NO_ACCOUNT_WITH_EMAIL_ERROR = "No existing account found with this email address";
    private static final String SAME_USER_TWICE_ERROR = "Not able to add the same user twice";
    private static final String ALREADY_CLIENT_ERROR = "User is already a client in another group";

    private GroupService groupService;
    private MemberService memberService;
    private UserService userService;
    private TaskListService taskListService;
    private TaskService taskService;

    public MemberController(GroupService groupService, MemberService memberService,
                            UserService userService, TaskListService taskListService, TaskService taskService) {
        this.groupService = groupService;
        this.memberService = memberService;
        this.userService = userService;
        this.taskListService = taskListService;
        this.taskService = taskService;
    }

    @GetMapping("/{groupId}")
    protected String showGroupDashboard(@PathVariable("groupId") Integer groupId, Model model) {
        userService.addGroupToLastThreeGroups(groupId);
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("taskListId", taskListService.getByGroupId(groupId).getTaskListId());
        model.addAttribute("taskList", taskService.getAllTasksByGroupId(groupId));
        model.addAttribute("allMembersByGroupId", memberService.getAllByGroupId(groupId));
        return "groupDashboard";
    }

    @GetMapping("/options/{groupId}")
    protected String showGroupOptions(@PathVariable("groupId") Integer groupId,
                                      @ModelAttribute("error") String error, Model model) {
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("allMembersByGroupId", memberService.getAllByGroupId(groupId));
        return "groupOptions";
    }

    @GetMapping("/options/edit/{groupId}")
    protected String showGroupEdit(@PathVariable("groupId") Integer groupId, Model model) {
        model.addAttribute("thisGroup", groupService.getById(groupId));
        return "groupEdit";
    }

    @PostMapping("/options/edit/{groupId}")
    protected String updateGroup(@ModelAttribute("group") GroupDTO group, BindingResult result) {
        if (!result.hasErrors()) {
            groupService.saveGroup(group);
        }
        return "redirect:/groups/options/{groupId}";
    }

    @RequestMapping(value = "/options/{groupId}/addmember")
    protected String doAddUser(@PathVariable("groupId") Integer groupId, Model model, String email,
                               @ModelAttribute ("addMember") MemberDTO addMember,
                               BindingResult result) {
        if (email != null) {
            Optional<Integer> userId = userService.findUserIdByEmail(email);

            checkIfAccountExists(groupId, addMember, result, userId);

            if (!result.hasErrors()) {
                memberService.saveMember(addMember);
                return "redirect:/groups/options/{groupId}";
            }
        }

        model.addAttribute("groupUserRole", new MemberDTO());
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("member", memberService.getAllByGroupId(groupId));
        return "groupAddMember";
    }

    @GetMapping("/options/{groupId}/updatemember/{userId}")
    protected String showGroupUpdateMember(@PathVariable("userId") Integer userId,
                                           @PathVariable("groupId") Integer groupId, Model model) {
        Optional<MemberDTO> member = memberService.findByUserIdAndGroupId(userId, groupId);
        if (member.isEmpty()) {
            return "redirect:/groups/options/{groupId}";
        }
        model.addAttribute("group", groupService.getById(groupId));
        model.addAttribute("member", member.get());
        return "groupUpdateMember";
    }

    @PostMapping("/options/{groupId}/updatemember/{userId}")
    protected String updateGroupMember(@PathVariable("groupId") Integer groupId,
                                       @ModelAttribute("member") MemberDTO member,
                                       BindingResult result) {
        checkForErrorsUpdateGroupMember(groupId, member, result);
        if (result.hasErrors()) {
            return  "groupUpdateMember";
        }
        memberService.saveMember(member);
        return "redirect:/groups/options/{groupId}";
    }

    @GetMapping("/options/{groupId}/delete/{userId}")
    protected String deleteUserFromGroup(@PathVariable("groupId") Integer groupId,
                                         @PathVariable("userId") Integer userId,
                                         RedirectAttributes redirectAttributes) {
        // checks first if the group member isn't the last group admin. If so, it can't be removed from the group.
        Optional<MemberDTO> member = memberService.findByUserIdAndGroupId(userId, groupId);
        if (member.isPresent()) {
            if (isLastAdminInGroup(member.get())) {
                redirectAttributes.addAttribute("error", ONLY_GROUP_ADMIN_ERROR);
            } else {
                memberService.deleteByUserId(userId, groupId);
            }
        }
        return "redirect:/groups/options/{groupId}";
    }

    private void checkIfAccountExists(Integer groupId, MemberDTO addMember, BindingResult result,
                                      Optional<Integer> userId) {
        if (!userId.isEmpty()) {
            addMember.setGroupId(groupId);
            addMember.setUserId(userId.get());;
            checkForErrorsAddMember(groupId, addMember, result, userId);
        } else {
            result.addError(new ObjectError("globalError", NO_ACCOUNT_WITH_EMAIL_ERROR));
        }
    }

    private void checkForErrorsAddMember(Integer groupId, MemberDTO makeMember, BindingResult result,
                                         Optional<Integer> userId) {
        if (memberService.userInGroupExists(makeMember)) {
            result.addError(new ObjectError("globalError", SAME_USER_TWICE_ERROR));
        }

        if (isAlreadyClient(groupId, makeMember, userId.get())) {
            result.addError(new ObjectError("globalError", ALREADY_CLIENT_ERROR));
        }
    }

    private void checkForErrorsUpdateGroupMember(Integer groupId, MemberDTO member, BindingResult result) {
        if (isAlreadyClient(groupId, member, member.getUserId())) {
            result.addError(new ObjectError("globalError", ALREADY_CLIENT_ERROR));
        }

        if (isLastAdminInGroup(member) && !member.isAdmin()) {
            result.addError(new ObjectError("globalError", ONLY_GROUP_ADMIN_ERROR));
        }
    }

    private boolean isAlreadyClient(Integer groupId, MemberDTO member, Integer userId) {
        return memberService.isClient(member) && memberService.isClientInOtherGroup(userId, groupId);
    }

    private boolean isLastAdminInGroup(MemberDTO member) {
        return memberService.findOutIfMemberIsAdmin(member) &&
                memberService.getGroupAdminsByGroupId(member.getGroupId()).size() == 1;
    }
}
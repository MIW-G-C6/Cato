package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
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
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("allTaskLists", taskListService.getAllByGroupId(groupId));
        return "groupDashboard";
    }

    @GetMapping("/options/{groupId}")
    protected String showGroupOptions(@PathVariable("groupId") Integer groupId, @ModelAttribute("error") String error, Model model) {
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("allMembersByGroupId", memberService.getAllByGroupId(groupId));
        return "groupOptions";
    }

    @GetMapping("/options/{groupId}/delete/{userId}")
    protected String deleteUserFromGroup(@PathVariable("groupId") Integer groupId,
                                         @PathVariable("userId") Integer userId,
                                         RedirectAttributes redirectAttributes) {
        // checks first if the group member isn't the last group admin. If so, it can't be removed from the group.
        Optional<MemberDTO> groupHasUser = memberService.findByUserIdAndGroupId(userId, groupId);
        if (groupHasUser.isPresent()) {
            if (memberService.findOutIfMemberIsAdmin(groupHasUser.get()) &&
                    memberService.getGroupAdminsByGroupId(
                            groupHasUser.get().getGroupId()).size() == 1) {
                redirectAttributes.addAttribute("error",
                        "Cannot remove group admin if this is the only group admin");
            } else {
                memberService.deleteByUserId(userId, groupId);
            }
        }
        return "redirect:/groups/options/{groupId}";
    }

    @GetMapping("/options/edit/{groupId}")
    protected String showGroupEdit(@PathVariable("groupId") Integer groupId, Model model) {
        model.addAttribute("thisGroup", groupService.getById(groupId));
//        model.addAttribute("allGroupHasUsersByGroupId", groupHasUsersService.getAllByGroupId(groupId));
        //TODO allGroupHasUsersByGroupId not used in html
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
                               @ModelAttribute ("makeMember") MemberDTO makeMember,
                               BindingResult result) {
        String validEntry = "";

        if (email != null) {
            Optional<UserDTO> user = userService.findUserByEmail(email); //TODO only use userId, ono user model
            if (!user.isEmpty()) {
                makeMember.setGroupId(groupId);
                makeMember.setUserId(user.get().getUserId());
                checkForErrorsAddMember(groupId, makeMember, result, user);
            } else {
                result.addError(new ObjectError("globalError", "No existing account found with this email address"));
            }
            if (!result.hasErrors()) {
                memberService.saveMember(makeMember);
                memberService.createNewTaskList(makeMember);
                validEntry = "Successfully added this member to your group";
            }
        }

        model.addAttribute("validEntry", validEntry);
        model.addAttribute("groupUserRole", new MemberDTO());
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("member", memberService.getAllByGroupId(groupId));
        return "groupAddMember";
    }

    private void checkForErrorsAddMember(Integer groupId, MemberDTO makeMember, BindingResult result, Optional<UserDTO> user) {
        if (memberService.userInGroupExists(makeMember)) {
            result.addError(new ObjectError("globalError", "Not able to add the same user twice"));
        }

        if (memberService.isClient(makeMember) && memberService.isClientInOtherGroup(user.get().getUserId(), groupId)) {
            result.addError(new ObjectError("globalError", "User is already a client in another group"));
        }
    }

    @GetMapping("/options/{groupId}/updatemember/{userId}")
    protected String showGroupUpdateMember(@PathVariable("userId") Integer userId,
                                           @PathVariable("groupId") Integer groupId, Model model) {
        Optional<MemberDTO> member = memberService.findByUserIdAndGroupId(userId, groupId);
        if (member.isEmpty()) {
            return "redirect:/groups/options/{groupId}";
        }
        model.addAttribute("groupUserRole", new MemberDTO());
        model.addAttribute("member", member.get());
        return "groupUpdateMember";
    }

    @PostMapping("/options/{groupId}/updatemember/{userId}")
    protected String updateGroupMember(@PathVariable("userId") Integer userId,
                                       @PathVariable("groupId") Integer groupId,
                                       @ModelAttribute("member") MemberDTO member,
                                       BindingResult result) {
        checkForErrorsUpdateGroupMember(groupId, member, result);
        if (result.hasErrors()) {
            return  "groupUpdateMember";
        }
        memberService.createNewTaskList(member);
        memberService.saveMember(member);
        return "redirect:/groups/options/{groupId}";
    }

    private void checkForErrorsUpdateGroupMember(Integer groupId, MemberDTO member, BindingResult result) {
        if (memberService.isClient(member) && memberService.isClientInOtherGroup(member.getUserId(), groupId)) {
            result.addError(new ObjectError("globalError", "This user is already a client in another group"));
        }

        if (memberService.findOutIfMemberIsAdmin(member) &&
                !member.isAdmin() &&
                memberService.getGroupAdminsByGroupId(member.getGroupId()).size() == 1) {
            result.addError(new ObjectError("globalError",
                    "Cannot remove admin rights if this member is the last admin in the group"));
        }
    }

    @GetMapping("{groupId}/clientDashboard/{clientId}")
    protected String showClientDashboard(@PathVariable("groupId") Integer groupId,
                                         @PathVariable("clientId") Integer clientId, Model model) {
        model.addAttribute("client", userService.getById(clientId));
        model.addAttribute("taskList", taskListService.findByUser(userService.getById(clientId)));
        model.addAttribute("allMembersByGroupId", memberService.getAllByGroupId(groupId));
        model.addAttribute("groupId", groupId);
        model.addAttribute("listOfTasks", taskService.getAllTasksByClientId(clientId));
        return "clientDashboard";
    }

//    protected void createNewTaskList(MemberDTO member) {
//        if (memberService.isClient(member)) {
//
//            TaskListDTO taskList = taskListService.findByUser(member.getUser());
//            if (taskList == null) {
//                taskListService.save(new TaskListDTO(member.getUser()));
//            }
//        }
//    }

//    protected boolean isClient(MemberDTO member) {
//        return member.getRole().equals(MemberDTO.getGroupRoleOptions()[1]);
//    }
}
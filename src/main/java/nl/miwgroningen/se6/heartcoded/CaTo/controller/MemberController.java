package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskListService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    public MemberController(GroupService groupService, MemberService memberService,
                            UserService userService, TaskListService taskListService) {
        this.groupService = groupService;
        this.memberService = memberService;
        this.userService = userService;
        this.taskListService = taskListService;
    }

    @GetMapping("/{groupId}")
    protected String showGroupDashboard(@PathVariable("groupId") Integer groupId, Model model) {
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("allTaskLists", taskListService.findAllByGroupId(groupId));
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
        Optional<GroupHasUsersDTO> groupHasUser = memberService.findByUserIdAndGroupId(userId, groupId);
        if (groupHasUser.isPresent()) {
            if (memberService.findOutIfMemberIsAdmin(groupHasUser.get()) &&
                    memberService.getGroupAdminsByGroupId(
                            groupHasUser.get().getGroup().getGroupId()).size() == 1) {
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
                               @ModelAttribute ("makeMember") GroupHasUsersDTO makeMember,
                               BindingResult result) {
        String validEntry = "";

        if (email != null) {
            Optional<UserDTO> user = userService.findUserByEmail(email); //TODO only use userId, ono user model
            if (!user.isEmpty()) {
                makeMember.setGroup(groupService.getById(groupId));
                makeMember.setUser(user.get());
                checkForErrorsAddMember(groupId, makeMember, result, user);
            } else {
                result.addError(new ObjectError("globalError", "No existing account found with this email address"));
            }
            if (!result.hasErrors()) {
                memberService.saveMember(makeMember);
                createNewTaskList(makeMember);
                validEntry = "Successfully added this member to your group";
            }
        }

        model.addAttribute("validEntry", validEntry);
        model.addAttribute("groupUserRole", new GroupHasUsersDTO());
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("member", memberService.getAllByGroupId(groupId));
        return "groupAddMember";
    }

    private void checkForErrorsAddMember(Integer groupId, GroupHasUsersDTO makeMember, BindingResult result, Optional<UserDTO> user) {
        if (memberService.userInGroupExists(makeMember)) {
            result.addError(new ObjectError("globalError", "Not able to add the same user twice"));
        }

        if (isClient(makeMember) && memberService.isClientInOtherGroup(user.get(), groupId)) {
            result.addError(new ObjectError("globalError", "User is already a client in another group"));
        }
    }

    @GetMapping("/options/{groupId}/updatemember/{userId}")
    protected String showGroupUpdateMember(@PathVariable("userId") Integer userId,
                                           @PathVariable("groupId") Integer groupId, Model model) {
        Optional<GroupHasUsersDTO> member = memberService.findByUserIdAndGroupId(userId, groupId);
        if (member.isEmpty()) {
            return "redirect:/groups/options/{groupId}";
        }
        model.addAttribute("groupHasUser", member.get());
        return "groupUpdateMember";
    }

    @PostMapping("/options/{groupId}/updatemember/{userId}")
    protected String updateGroupMember(@PathVariable("userId") Integer userId,
                                       @PathVariable("groupId") Integer groupId,
                                       @ModelAttribute("groupHasUser") GroupHasUsersDTO groupHasUser,
                                       BindingResult result) {
        checkForErrorsUpdateGroupMember(groupId, groupHasUser, result);
        if (result.hasErrors()) {
            return  "groupUpdateMember";
        }
        createNewTaskList(groupHasUser);
        memberService.saveMember(groupHasUser);
        return "redirect:/groups/options/{groupId}";
    }

    private void checkForErrorsUpdateGroupMember(Integer groupId, GroupHasUsersDTO member, BindingResult result) {
        if (isClient(member) && memberService.isClientInOtherGroup(member.getUser(), groupId)) {
            result.addError(new ObjectError("globalError", "This user is already a client in another group"));
        }

        if (memberService.findOutIfMemberIsAdmin(member) &&
                !member.isAdmin() &&
                memberService.getGroupAdminsByGroupId(member.getGroup().getGroupId()).size() == 1) {
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
        return "clientDashboard";
    }

    protected void createNewTaskList(GroupHasUsersDTO member) {
        if (isClient(member)) {

            TaskListDTO taskList = taskListService.findByUser(member.getUser());
            if (taskList == null) {
                taskListService.save(new TaskListDTO(member.getUser()));
            }
        }
    }

    protected boolean isClient(GroupHasUsersDTO member) {
        return member.getUserRole().equals(GroupHasUsersDTO.getGroupRoleOptions()[1]);
    }
}
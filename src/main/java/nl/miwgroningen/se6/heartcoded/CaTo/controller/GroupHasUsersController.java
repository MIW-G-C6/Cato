package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupHasUsersService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Controls the groupDashboard page
 */

@Controller
@RequestMapping("/groups")
public class GroupHasUsersController {

    private GroupService groupService;
    private GroupHasUsersService groupHasUsersService;
    private UserService userService;

    public GroupHasUsersController(GroupService groupService, GroupHasUsersService groupHasUsersService, UserService userService) {
        this.groupService = groupService;
        this.groupHasUsersService = groupHasUsersService;
        this.userService = userService;
    }

    @GetMapping("/{groupId}")
    protected String showGroupDashboard(@PathVariable("groupId") Integer groupId, Model model) {
    model.addAttribute("thisGroup", groupService.getById(groupId));
    return "groupDashboard";
    }

    @GetMapping("/options/{groupId}")
    protected String showGroupOptions(@PathVariable("groupId") Integer groupId, Model model) {
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("allGroupHasUsersByGroupId", groupHasUsersService.getAllByGroupId(groupId));
        return "groupOptions";
    }

    @PostMapping("/options/{groupId}")
    protected String updateUserRole(
            @ModelAttribute("groupHasUser") GroupHasUsers groupHasUsers, BindingResult result) {
        if (!result.hasErrors()) {
            groupHasUsersService.saveGroupHasUsers(groupHasUsers);
        }
        return "redirect/groups/options/{groupId}";
    }

    @GetMapping("/options/edit/{groupId}")
    protected String showGroupEdit(@PathVariable("groupId") Integer groupId, Model model) {
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("allGroupHasUsersByGroupId", groupHasUsersService.getAllByGroupId(groupId));
        return "groupEdit";
    }

    @PostMapping("/options/edit/{groupId}")
    protected String updateGroup(@ModelAttribute("group") Group group, BindingResult result) {
        if (!result.hasErrors()) {
            groupService.saveGroup(group);
        }
        return "redirect:/groups/options/{groupId}";
    }

    @RequestMapping(value = "/options/{groupId}/editmember")
    protected String doAddUser(@PathVariable("groupId") Integer groupId, Model model, String email) {
        if (email != null) {
            Optional<User> user = userService.findUserByEmail(email);
            if (!user.isEmpty()) {
                groupHasUsersService.saveGroupHasUsers(new GroupHasUsers(groupService.getById(groupId), user.get(), GroupHasUsers.getGroupRoleOptions()[0]));
            }
        }
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("groupHasUsers", groupHasUsersService.getAllByGroupId(groupId));
        return "groupEditMember";
    }
}
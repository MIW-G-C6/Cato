package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupHasUsersService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskListService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    private TaskListService taskListService;

    public GroupHasUsersController(GroupService groupService, GroupHasUsersService groupHasUsersService,
                                   UserService userService, TaskListService taskListService) {
        this.groupService = groupService;
        this.groupHasUsersService = groupHasUsersService;
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

    @GetMapping("/options/{groupId}/delete/{userId}")
    protected String deleteUserFromGroup(@PathVariable("groupId") Integer groupId,
                                         @PathVariable("userId") Integer userId) {
        groupHasUsersService.deleteByUserId(userId, groupId);
        return "redirect:/groups/options/{groupId}";
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
    protected String doAddUser(@PathVariable("groupId") Integer groupId, Model model, String email,
                               @ModelAttribute ("makeGroupHasUsers") GroupHasUsers makeGroupHasUsers,
                               BindingResult result) {
        if (email != null) {
            Optional<User> user = userService.findUserByEmail(email);
            if (!user.isEmpty()) {
                makeGroupHasUsers.setGroup(groupService.getById(groupId));
                makeGroupHasUsers.setUser(user.get());
                if (!result.hasErrors()) {
                    groupHasUsersService.saveGroupHasUsers(makeGroupHasUsers);
                }
                createNewTaskList(makeGroupHasUsers);
            }
        }
        model.addAttribute("groupUserRole", new GroupHasUsers());
        model.addAttribute("thisGroup", groupService.getById(groupId));
        model.addAttribute("groupHasUsers", groupHasUsersService.getAllByGroupId(groupId));
        return "groupEditMember";
    }

    @GetMapping("{groupId}/clientDashboard/{clientId}")
    protected String showClientDashboard(@PathVariable("groupId") Integer groupId, @PathVariable("clientId") Integer clientId, Model model) {
        model.addAttribute("client", userService.getById(clientId));
        model.addAttribute("taskList", taskListService.findByUser(userService.getById(clientId)));
        model.addAttribute("allGroupHasUsersByGroupId", groupHasUsersService.getAllByGroupId(groupId));
        return "clientDashboard";
    }

    protected void createNewTaskList(GroupHasUsers groupHasUsers) {
        if (groupHasUsers.getUserRole().equals(GroupHasUsers.getGroupRoleOptions()[1])) {   //if user is a client

            TaskList taskList = taskListService.findByUser(groupHasUsers.getUser());
            if (taskList == null) {
                taskListService.save(new TaskList(groupHasUsers.getUser()));
            }
        }
    }
}
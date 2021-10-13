package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupHasUsersService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls the group overview page.
 */

@Controller
public class GroupController {

    private GroupService groupService;
    private GroupHasUsersService groupHasUsersService;
    private UserService userService;

    public GroupController(GroupService groupService, GroupHasUsersService groupHasUsersService, UserService userService) {
        this.groupService = groupService;
        this.groupHasUsersService = groupHasUsersService;
        this.userService = userService;
    }

    @GetMapping("/groups")
    protected String showGroupOverview(Model model) {
        model.addAttribute("allGroups", groupService.findAllGroups());
        return "groupOverview";
    }

    @GetMapping("/groups/new")
    protected String showGroupForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("group", new Group());
        model.addAttribute("allGroups", groupService.findAllGroups());
        return "groupForm";
    }

    @GetMapping("/groups/delete/{groupId}")
    protected String deleteGroupBySiteAdmin(@PathVariable("groupId") Integer groupId) {
        groupService.deleteGroupById(groupId);
        return "redirect:/groups";
    }

    @PostMapping("/groups/new")
    protected String saveOrUpdateGroup(@ModelAttribute("group") Group group,
                                       @ModelAttribute("groupHasUsers") GroupHasUsers groupHasUsers,
                                       @ModelAttribute("user") User user, BindingResult result) {
        if (!result.hasErrors()) {
            groupService.saveGroup(group);
            groupHasUsersService.saveGroupHasUsers(new GroupHasUsers(group, user, "groupAdmin"));
        }
        return "redirect:/groups/new";
    }

    @GetMapping("/groups/options/edit/{groupId}")
    protected String showGroupEdit(@PathVariable("groupId") Integer groupId, Model model) {
        model.addAttribute("thisGroup", groupService.getById(groupId));
        return "groupEdit";
    }

    @PostMapping("/groups/options/edit/{groupId}")
    protected String updateGroup(@ModelAttribute("group") Group group, BindingResult result) {
        if (!result.hasErrors()) {
            groupService.saveGroup(group);
        }
        return "redirect:/groups/options/{groupId}";
    }


}
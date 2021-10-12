package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupHasUsersService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



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

    public GroupHasUsersController(GroupService groupService, GroupHasUsersService groupHasUsersService) {
        this.groupService = groupService;
        this.groupHasUsersService = groupHasUsersService;
    }

    @GetMapping("/{groupId}")
    protected String showGroupDashboard(@PathVariable("groupId") Integer groupId, Model model) {
    model.addAttribute("thisGroup", groupService.getById(groupId));
    return "groupDashboard";
    }

    @GetMapping("/options/{groupId}")
    protected String showGroupOptions(@PathVariable("groupId") Integer groupId, Model model) {
        model.addAttribute("thisGroup", groupService.getById(groupId));
        return "groupOptions";
    }

    @GetMapping("/options/edit/{groupId}")
    protected String showGroupEdit(@PathVariable("groupId") Integer groupId, Model model) {
        model.addAttribute("thisGroup", groupService.getById(groupId));
        return "groupEdit";
    }

    @PostMapping("/options/edit/{groupId}")
    protected String updateGroup(@ModelAttribute("group") Group group, BindingResult result) {
        if (!result.hasErrors()) {
            groupService.saveGroup(group);
        }
        return "redirect:/groups/options/{groupId}";
    }
}
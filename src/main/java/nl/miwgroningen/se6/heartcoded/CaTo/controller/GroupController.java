package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private MemberService memberService;
    private UserService userService;

    public GroupController(GroupService groupService, MemberService memberService, UserService userService) {
        this.groupService = groupService;
        this.memberService = memberService;
        this.userService = userService;
    }

    @GetMapping("/groups")
    protected String showGroupOverview(Model model) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("allGroups", memberService.getAllGroupsByUserId(user.getUserId()));
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
    protected String saveOrUpdateGroup(@ModelAttribute("group") GroupDTO group,
                                       @ModelAttribute("member") Member member, BindingResult result) {
        if (!result.hasErrors()) {
            groupService.saveGroup(group);
            UserDTO user = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            memberService.saveMember(new GroupHasUsersDTO(group, user, "Caregiver", true));
        }
        return "redirect:/groups/" + group.getGroupId();
    }
}
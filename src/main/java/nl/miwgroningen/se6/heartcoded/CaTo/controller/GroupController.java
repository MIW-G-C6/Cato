package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
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
        model.addAttribute("allGroups", memberService.getAllGroupsByUserId(userService.getCurrentUser().getUserId()));
        return "groupOverview";
    }

    @GetMapping("/groups/new")
    protected String showGroupForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("group", new GroupDTO());
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
                                       @ModelAttribute("member") MemberDTO member, BindingResult result) {
        if (!result.hasErrors()) {
            groupService.saveGroup(group);
            UserDTO userDTO = userService.getCurrentUser();
            System.out.println("*****************"+ userDTO.getUserId() + "****************" + group.getGroupId());
            memberService.saveMember(new MemberDTO(userDTO.getUserId(), userDTO.getName(), group.getGroupId(), "Caregiver", true));
        }
        return "redirect:/groups/" + group.getGroupId();
    }
}
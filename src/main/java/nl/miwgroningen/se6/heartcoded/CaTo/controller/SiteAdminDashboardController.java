package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskListService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Controls the site admin dashboard page
 */
@Controller
public class SiteAdminDashboardController {

    private UserService userService;
    private GroupService groupService;
    private MemberService memberService;

    public SiteAdminDashboardController(UserService userService, GroupService groupService, MemberService memberService) {
        this.userService = userService;
        this.groupService = groupService;
        this.memberService = memberService;
    }

    @GetMapping("/siteAdminDashboard")
    protected String showSiteAdminDashboard(@ModelAttribute("error") String error, Model model) {
        model.addAttribute("allUsers", userService.findAllRegisteredUsers());
        model.addAttribute("allClients", memberService.findAllClientsForSiteAdmin());
        model.addAttribute("allGroups", groupService.findAllGroups());

        return "siteAdminDashboard";
    }
}

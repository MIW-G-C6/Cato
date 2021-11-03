package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskListService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    private TaskListService taskListService;

    public SiteAdminDashboardController(UserService userService, GroupService groupService,
                                        MemberService memberService, TaskListService taskListService) {
        this.userService = userService;
        this.groupService = groupService;
        this.memberService = memberService;
        this.taskListService = taskListService;
    }

    @GetMapping("/siteAdminDashboard")
    protected String showSiteAdminDashboard(Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("allTaskLists", taskListService.findAll());
        model.addAttribute("allClients", memberService.findAllClients());
        model.addAttribute("allGroups", groupService.findAllGroups());

        return "siteAdminDashboard";
    }
}

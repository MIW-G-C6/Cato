package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.CircleService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Controls the site admin dashboard page.
 */

@Controller
public class SiteAdminController {

    private CircleService circleService;
    private MemberService memberService;
    private UserService userService;
    private TaskService taskService;

    public SiteAdminController(CircleService circleService, MemberService memberService, UserService userService,
                               TaskService taskService) {
        this.circleService = circleService;
        this.memberService = memberService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/siteAdmin/dashboard")
    protected String showSiteAdminDashboard(Model model) {
        model.addAttribute("numberOfUsers", userService.totalNumberOfUsers());
        model.addAttribute("numberOfCircles", circleService.totalNumberOfCircles());
        model.addAttribute("numberOfClients", memberService.totalNumberOfClients());
        model.addAttribute("numberOfTasks", taskService.totalNumberOfTasks());
        model.addAttribute("numberOfReservedTasks", taskService.totalNumberOfReservedTasks());
        return "siteAdminDashboard";
    }

    @GetMapping("/siteAdmin/userOverview")
    protected String showSiteAdminUserOverview(@ModelAttribute("error") String error) {
        return "siteAdminUserOverview";
    }

    @GetMapping("/siteAdmin/circleClientOverview")
    protected String showSiteAdminCircleClientOverview(@ModelAttribute("error") String error, HttpSession session) {
        session.setAttribute("circleDeleteRedirect", "redirect:/siteAdmin/circleClientOverview");
        return "siteAdminCircleClientOverview";
    }
}

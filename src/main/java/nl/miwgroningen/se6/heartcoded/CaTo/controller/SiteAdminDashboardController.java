package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.CircleService;
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

    private CircleService circleService;
    private MemberService memberService;

    public SiteAdminDashboardController(CircleService circleService, MemberService memberService) {
        this.circleService = circleService;
        this.memberService = memberService;
    }

    @GetMapping("/siteAdmin/dashboard")
    protected String showSiteAdminDashboard(@ModelAttribute("error") String error, Model model) {
        model.addAttribute("allClients", memberService.findAllClientsForSiteAdmin());
        model.addAttribute("allCircles", circleService.findAllCircles());

        return "siteAdminDashboard";
    }

    @GetMapping("/siteAdmin/userOverview")
    protected String showSiteAdminUserOverview(@ModelAttribute("error") String error, Model model) {
        model.addAttribute("allClients", memberService.findAllClientsForSiteAdmin());

        return "siteAdminUserOverview";
    }
}

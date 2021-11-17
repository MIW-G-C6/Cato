package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.CircleService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Controls the site admin dashboard page
 */
@Controller
public class SiteAdminController {

    private CircleService circleService;
    private MemberService memberService;
    private UserService userService;

    public SiteAdminController(CircleService circleService, MemberService memberService, UserService userService) {
        this.circleService = circleService;
        this.memberService = memberService;
        this.userService = userService;
    }

    @GetMapping("/siteAdmin/dashboard")
    protected String showSiteAdminDashboard(@ModelAttribute("error") String error, Model model) {
        if (isNotSiteAdmin()) {
            return "redirect:/403";
        }

        model.addAttribute("allClients", memberService.findAllClientsForSiteAdmin());
        model.addAttribute("allCircles", circleService.findAllCircles());
        return "siteAdminDashboard";
    }

    @GetMapping("/siteAdmin/userOverview")
    protected String showSiteAdminUserOverview(@ModelAttribute("error") String error) {
        if (isNotSiteAdmin()) {
            return "redirect:/403";
        }
        return "siteAdminUserOverview";
    }

    @GetMapping("/siteAdmin/circleClientOverview")
    protected String showSiteAdminCircleClientOverview(@ModelAttribute("error") String error, HttpSession session) {
        if (isNotSiteAdmin()) {
            return "redirect:/403";
        }

        session.setAttribute("circleDeleteRedirect", "redirect:/siteAdmin/circleClientOverview");
        return "siteAdminCircleClientOverview";
    }

    private boolean isNotSiteAdmin() {
        return !userService.currentUserIsSiteAdmin();
    }

}

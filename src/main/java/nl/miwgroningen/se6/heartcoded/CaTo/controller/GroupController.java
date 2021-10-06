package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Controls the group overview page.
 */

@Controller
public class GroupController {

    private GroupRepository groupRepository;

    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping("/groups")
    protected String showGroupOverview(Model model) {
        model.addAttribute("allGroups", groupRepository.findAll());
        return "groupOverview";
    }
}

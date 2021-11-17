package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.*;
import nl.miwgroningen.se6.heartcoded.CaTo.service.CircleService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Controls the search function response on the siteAdminDashboard page
 */
@RestController
public class SiteAdminSearchController {

    private UserService userService;
    private CircleService circleService;

    public SiteAdminSearchController(UserService userService, CircleService circleService) {
        this.userService = userService;
        this.circleService = circleService;
    }

    @PostMapping("/siteAdmin/dashboard/searchList")
    protected ResponseEntity<?> getUserSearchResult(@Valid @RequestBody UserSearchDTO keywords, Errors errors) {

        UserSearchAjaxResponseBody result = new UserSearchAjaxResponseBody();

        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);
        }

        List<UserDTO> userList = userService.findWithNameContains(keywords.getKeywords());

        if (userList.isEmpty()) {
            result.setMsg("No users found");
        } else {
            result.setMsg("Succes");
        }
        result.setUsers(userList);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/siteAdmin/circleClientOverview/searchList")
    protected ResponseEntity<?> getCircleSearchResult(@Valid @RequestBody CircleSearchDTO keywords, Errors errors) {

        CircleSearchResponseBody result = new CircleSearchResponseBody();

        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);
        }

        List<CircleClientDTO> circleClientList = circleService.findWithNameContains(keywords.getKeywords());

        if (circleClientList.isEmpty()) {
            result.setMsg("No care circles found");
        } else {
            result.setMsg("Succes");
        }
        result.setCircles(circleClientList);

        return ResponseEntity.ok(result);
    }

}

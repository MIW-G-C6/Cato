package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserSearchAjaxResponseBody;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserSearchDTO;
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

    public SiteAdminSearchController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/siteAdminDashboard/searchList")
    protected ResponseEntity<?> getSearchResult(@Valid @RequestBody UserSearchDTO keywords, Errors errors) {

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
}

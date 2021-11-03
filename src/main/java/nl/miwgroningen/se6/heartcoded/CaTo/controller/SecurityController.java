package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 * <p>
 * Dit is wat het programma doet
 */

@Controller
public class SecurityController {

    @RequestMapping(value = "/userId", method = RequestMethod.GET)
    @ResponseBody
    public Integer currentUserId(Principal principal) {
        UserDTO userDTO = (UserDTO) principal;
        return userDTO.getUserId();
    }
}


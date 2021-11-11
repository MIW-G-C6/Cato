package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 * <p>
 * Dit is wat het programma doet
 */

@Controller
public class CalendarController {

    @GetMapping("/calendar")
    protected String showCalendar() {
        return "calendar";
    }

}

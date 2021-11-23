package nl.miwgroningen.se6.heartcoded.CaTo.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Handles the exception for exceeded file size limit.
 */

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileSizeLimitExceededException(MaxUploadSizeExceededException exc,
                                                       RedirectAttributes redirectAttributes, HttpSession session) {
        redirectAttributes.addAttribute("error", "File too large");
        Integer userId = (Integer) session.getAttribute("lastUserId");
        Integer circleId = (Integer) session.getAttribute("lastCircleId");

        if ((boolean) session.getAttribute("editUser")) {
            return "redirect:/users/edit/" + userId;
        }
        if ((boolean) session.getAttribute("editCircle")) {
            return "redirect:/circles/options/edit/" + circleId;
        }
        return "";
    }
}

package nl.miwgroningen.se6.heartcoded.CaTo.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Handles the exception for *********************
 */
@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileSizeLimitExceededException(MaxUploadSizeExceededException exc,
                                                       RedirectAttributes redirectAttributes, HttpSession session) {
        redirectAttributes.addAttribute("error", "File too large");
        Integer userId = (Integer) session.getAttribute("currentUserId");
        return "redirect:/users/edit/" + userId;        //Exception redirects back to the users edit profile page by default.
    }
}

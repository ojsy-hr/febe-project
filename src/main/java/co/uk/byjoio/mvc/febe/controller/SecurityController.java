package co.uk.byjoio.mvc.febe.controller;

import co.uk.byjoio.mvc.febe.service.UserService;
import co.uk.byjoio.mvc.febe.user.WebUser;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Separate controller to handle Security side of the system.
 * Not strictly required but improves readability/maintainability.
 *
 * @author ojsy-hr
 * @version v1.0.0
 * @since 13-09-2026
 */
@Controller
public class SecurityController {

    /**
     * Debug logging - can be removed
     */
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    /**
     * The service used to retrieve User data
     */
    private final UserService userService;

    /**
     * Injects the UserService as a dependency
     * @param userService to retrieve User data
     */
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @return login.html
     */
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    /**
     * @param model of the web-page to edit
     * @return sign-up.html
     */
    @GetMapping("/sign-up")
    public String showSignUp(Model model) {

        model.addAttribute("webUser", new WebUser());

        return "sign-up";
    }

    /**
     * @return access-denied.html
     */
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

    /**
     * @param webUser
     * @param bindingResult
     * @param model
     * @return sign-up.html if errors during sign-up, else redirect to login.html
     */
    @PostMapping("/sign-up")
    public String createNewUser(@Valid @ModelAttribute("webUser") WebUser webUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "sign-up";
        }

        // if user email in use, display error to user - return sign-up.html
        if (userService.findByEmail(webUser.getEmail()).isPresent()) {
            model.addAttribute("signUpError", "That email address is already registered.");

            return "sign-up";
        }

        // save user to DB
        userService.register(webUser);
        logger.info("Created account for {}", webUser.getEmail());

        return "redirect:/login?signup";
    }
}
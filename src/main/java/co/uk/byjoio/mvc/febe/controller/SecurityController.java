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

@Controller
public class SecurityController {

    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String showSignUp(Model model) {

        model.addAttribute("webUser", new WebUser());

        return "sign-up";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

    @PostMapping("/sign-up")
    public String createNewUser(@Valid @ModelAttribute("webUser") WebUser webUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "sign-up";
        }

        if (userService.findByEmail(webUser.getEmail()).isPresent()) {
            model.addAttribute("signUpError", "That email address is already registered.");

            return "sign-up";
        }

        userService.register(webUser);
        logger.info("Created account for {}", webUser.getEmail());

        return "redirect:/login?signup";
    }
}

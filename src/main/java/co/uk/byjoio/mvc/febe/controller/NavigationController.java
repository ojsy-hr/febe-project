package co.uk.byjoio.mvc.febe.controller;

import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.entity.UserProfile;
import co.uk.byjoio.mvc.febe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Separate controller to handle normal navigation side of the system.
 * Not strictly required but improves readability/maintainability.
 *
 * @author ojsy-hr
 * @version v1.0.0
 * @since 13-09-2026
 */
@Controller
public class NavigationController {

    /**
     * The service used to retrieve User data
     */
    private final UserService userService;

    /**
     * Injects the UserService as a dependency
     * @param userService to retrieve User data
     */
    public NavigationController(UserService userService){
        this.userService = userService;
    }

    /**
     * @return landing.html
     */
    @GetMapping("/")
    public String showLanding() {
        return "landing";
    }

    /**
     * @return home.html
     */
    @GetMapping("/home")
    public String showHome() {

        return "home";
    }

    @GetMapping("/profile")
    public String showProfile(Model model){

        UserProfile userProfile = userService.findUserProfileById()
                .orElseThrow(() -> new IllegalArgumentException("No profile exists for user"));

        model.addAttribute("userProfile", userProfile);
        return "profile";
    }

    @GetMapping("/profile/edit-profile")
    public String editProfile(@RequestParam("user_profile_id")int userId, Model model){

        UserProfile userProfile = userService.findUserProfileById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No profile exists for user"));

        model.addAttribute("userProfile", userProfile);

        return "profile-form";
    }

    @PostMapping("/profile/save")
    public String saveProfile(@ModelAttribute("userProfile") UserProfile profile){

        userService.saveUserProfile(profile);

        return "redirect:/profile";
    }
}
package co.uk.byjoio.mvc.febe.controller;

import co.uk.byjoio.mvc.febe.entity.Role;
import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Separate controller to handle Admin side of the system.
 * Not strictly required but improves readability/maintainability.
 *
 * @author ojsy-hr
 * @version v1.0.0
 * @since 13-09-2026
 */
@Controller
@RequestMapping("/system")
public class AdminController {

    /**
     * The service used to retrieve User data
     */
    private final UserService userService;

    /**
     * Injects the UserService as a dependency
     * @param userService to retrieve User data
     */
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param model of the web-page to edit
     * @return system.html web-page
     */
    @GetMapping("/manage-users")
    public String listUsers(Model model) {

        // hit the database to find all users, add to system.html as an attribute
        model.addAttribute("users", userService.findAll());

        return "system";
    }

    /**
     * @param email of user to edit, passed in as a param from the form
     * @param model of the web-page to edit
     * @return user-form.html - this is where edits take place on the front-end
     */
    @GetMapping("/edit-user")
    public String editUser(@RequestParam("email") String email, Model model) {

        // find user by email
        User user = userService.findByEmail(email)
                // or throw error if user doesn't exist
                .orElseThrow(() -> new IllegalArgumentException("No user with email " + email));

        // show user on web-page
        model.addAttribute("user", user);

        // show users roles on web-page
        model.addAttribute("allRoles", userService.findAllRoles());
        model.addAttribute("grantedRoleIds", user.getRoles().stream().map(Role::getRoleId).toList());

        return "user-form";
    }

    /**
     * @param user object to save in database
     * @param roleIds to save back to database
     * @return a redirect back to the manage-users.html page
     */
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(name="roleIds", required=false) List<Integer> roleIds) {

        // apply roles to user - empty list or roleIds
        userService.updateAccount(user, roleIds == null ? List.of() : roleIds);

        // redirect back to manage-users page
        return "redirect:/system/manage-users";
    }
}
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

@Controller
@RequestMapping("/system")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/manage-users")
    public String listUsers(Model model) {

        model.addAttribute("users", userService.findAll());

        return "system";
    }

    @GetMapping("/edit-user")
    public String editUser(@RequestParam("email") String email, Model model) {

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user with email " + email));

        model.addAttribute("user", user);
        model.addAttribute("allRoles", userService.findAllRoles());
        model.addAttribute("grantedRoleIds", user.getRoles().stream().map(Role::getRoleId).toList());

        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(name="roleIds", required=false) List<Integer> roleIds) {

        userService.updateAccount(user, roleIds == null ? List.of() : roleIds);

        return "redirect:/system/manage-users";
    }
}

package co.uk.byjoio.mvc.febe.controller;

import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.service.UserService;
import co.uk.byjoio.mvc.febe.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.logging.Logger;

@Controller
public class SecurityController {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final UserService userService;

    @Autowired
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLogin(){

        return "login";
    }

    @GetMapping("/sign-up")
    public String showSignUp(Model model){

        model.addAttribute("webUser", new WebUser());

        return "sign-up";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied(){

        return "access-denied";
    }

    @PostMapping("/createNewUser")
    public String createNewUser(@Valid @ModelAttribute("webUser")WebUser webUser, BindingResult bindingResult, HttpSession session, Model model){

        String userName = webUser.getEmail();
        logger.info("Processing sign-up form for: " + userName);

        // form validation
        if(bindingResult.hasErrors()){
            return "/sign-up";
        }

        // check the database if user already exists
        User user = userService.findByUserName(userName);
        if(user != null){
            model.addAttribute("webUser", new WebUser());
            model.addAttribute("signUpError", "User name already exists.");

            logger.warning("User name already exists.");

            return "sign-up";
        }

        // create user account and store in the database
        userService.save(webUser);

        logger.info("Successfully created user: " + userName);

        // place user in the web http session for later
        session.setAttribute("user", webUser);

        return "login";
    }
}

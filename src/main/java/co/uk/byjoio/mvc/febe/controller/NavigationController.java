package co.uk.byjoio.mvc.febe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

    @GetMapping("/")
    public String showLanding() {
        return "landing";
    }

    @GetMapping("/home")
    public String showHome() {
        return "home";
    }
}

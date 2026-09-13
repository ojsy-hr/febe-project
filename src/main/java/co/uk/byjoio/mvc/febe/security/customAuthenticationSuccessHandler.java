package co.uk.byjoio.mvc.febe.security;

import java.io.IOException;

import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class customAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    private final UserService userService;

    public customAuthenticationSuccessHandler(UserService userService){
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{

        System.out.println("In AuthenticationSuccessHandler");

        String userName = authentication.getName();

        System.out.println("User Name=" + userName);

        User user = userService.findByUserName(userName);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        response.sendRedirect(request.getContextPath() + "/home");
    }
}
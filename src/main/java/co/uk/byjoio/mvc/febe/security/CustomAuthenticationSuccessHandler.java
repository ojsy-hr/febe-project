package co.uk.byjoio.mvc.febe.security;

import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user is missing from the database"));

        request.getSession().setAttribute("user", user);

        response.sendRedirect(request.getContextPath() + "/home");
    }
}

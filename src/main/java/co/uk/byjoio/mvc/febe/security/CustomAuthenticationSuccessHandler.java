package co.uk.byjoio.mvc.febe.security;

import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * On successful authentication, set user session and direct to home.html
 *
 * @author ojsy-hr
 * @version v1.0.0
 * @since 13-09-2026
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * The service used to retrieve User data
     */
    private final UserService userService;

    /**
     * Injects the UserService as a dependency
     * @param userService to retrieve User data
     */
    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param request the request which caused the successful authentication
     * @param response the response
     * @param authentication the <tt>Authentication</tt> object which was created during the authentication process.
     * @throws IOException if authenticated user not in the database
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // find authenticated user, else throw exception
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user is missing from the database"));

        request.getSession().setAttribute("user", user);

        response.sendRedirect(request.getContextPath() + "/home");
    }
}
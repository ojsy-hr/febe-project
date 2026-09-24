package co.uk.byjoio.mvc.febe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Role configuration, login/logout handling and Password encoding
 *
 * @author ojsy-hr
 * @version v1.0.0
 * @since 13-09-2026
 */
@Configuration
public class FebeSecurityConfig {

    /**
     * @param http to configure security
     * @param authenticationSuccessHandler to validate authentication
     * @return relevant page to user in web browser
     * @throws Exception if user access is denied
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationSuccessHandler authenticationSuccessHandler) throws Exception {

        http.authorizeHttpRequests(configurer -> configurer
                        // each endpoint explicitly accessed with the given role
                        .requestMatchers("/members/**").hasRole("MEMBER")
                        .requestMatchers("/system/**").hasRole("ADMIN")
                        // open to anyone, logged in or not
                        .requestMatchers(
                                "/",
                                "/sign-up/**",
                                "/access-denied").permitAll()
                        // catch all - any other url, user must be logged in/authenticated
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticate")
                        .successHandler(authenticationSuccessHandler)
                        .permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
                .exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));

        return http.build();
    }

    /**
     * Encodes users passwords
     * @return Bcrypt Password Encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package co.uk.byjoio.mvc.febe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class FebeSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationSuccessHandler authenticationSuccessHandler) throws Exception {

        http.authorizeHttpRequests(configurer -> configurer
                        // each endpoint explicitly accessed with the given role
                        .requestMatchers("/members/**").hasRole("MEMBER")
                        .requestMatchers("/system/**").hasRole("ADMIN")
                        // open to anyone, logged in or not
                        .requestMatchers("/", "/sign-up/**", "/access-denied").permitAll()
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

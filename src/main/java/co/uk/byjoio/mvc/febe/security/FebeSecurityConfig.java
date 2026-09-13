package co.uk.byjoio.mvc.febe.security;

import co.uk.byjoio.mvc.febe.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class FebeSecurityConfig{

    /**
     * @param dataSource bean injected to query against
     * @return User Manager with configured queries
     */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, email, password from users where user_id = ?"
        );

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select role_id, role from roles where role_id = (select role_id from users_roles where user_id = ?)"
        );

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, customAuthenticationSuccessHandler authenticationSuccessHandler) throws Exception{

        http.authorizeHttpRequests(configurer ->
                configurer
                    // each endpoint explicitly accessed with the given role
                    .requestMatchers("/members/**").hasRole("MEMBER")
                    .requestMatchers("/system/**").hasRole("ADMIN")
                    // any url starting with sign-up can be accessed by anyone
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/createNewUser").permitAll()
                    .requestMatchers("/sign-up/**").permitAll()
                    // catch all - any other url, user must be logged in/authenticated
                    .anyRequest().authenticated())
            .formLogin(form ->
                form
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .successHandler(authenticationSuccessHandler)
                    .permitAll())
            .logout(logout -> logout.permitAll())
            .exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        // No longer uses setUserDetailsService, now passed into the constructor
        // Set the custom user details service
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider(userService);
        // Set the password encoder - brcypt
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }
}
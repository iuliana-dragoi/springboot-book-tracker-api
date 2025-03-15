package com.crode.book_tracker_api.config;

import com.crode.book_tracker_api.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, Environment env) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth

                // Allow full access to the H2 console (only for development/testing)
                .requestMatchers("/h2-console/**").permitAll()

                // Require authentication for accessing the library page
                .requestMatchers("/library").authenticated()

                // Ensure only authenticated users can send a POST request to add a book to their list
                .requestMatchers(HttpMethod.POST, "/userBooks/addToList").authenticated()

                // Require authentication for any other request that hasn’t been explicitly allowed above
                .anyRequest().authenticated()
            )

            // Configure form-based login
            .formLogin(form -> form

                // After successful login, redirect users to /library
                .defaultSuccessUrl("/library", true)

                // Allow access to the login page for all users (including unauthenticated ones)
                .permitAll()
            )

            // Configure logout behavior
            .logout(logout -> logout

                // URL to trigger the logout process
                .logoutUrl("/logout")

                // Redirect to login page with a logout message after logging out
                .logoutSuccessUrl("/login?logout")

                // Allow all users (authenticated or not) to access logout
                .permitAll()
            )

            // Security headers configuration
            .headers(headers -> headers
                // Allow iframes from the same origin (necessary for the H2 database console to work)
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            )

            // Set the authentication provider for user authentication
            .authenticationProvider(authenticationProvider());

        // Disable CSRF protection for H2 Console only in development environment
        if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            // CSRF protection is disabled for H2 Console because it does not support CSRF tokens.
            // This should ONLY be used in development and not in production.
            http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        }

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}

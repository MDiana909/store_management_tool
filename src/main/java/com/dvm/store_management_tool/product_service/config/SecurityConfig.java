package com.dvm.store_management_tool.product_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Security filter chain for HTTP requests.
     * @param http the HttpSecurity object to configure.
     * @return the configured security filter chain.
     * @throws Exception if errors occur when building the filter chain.
     */
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/products/**").hasRole("MANAGER")
                        .requestMatchers("/api/orders/**").hasRole("STAFF")
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Bean for the password encoder that uses BCrypt hashing algorithm.
     * @return a PasswordEncoder used to encode the password for the AuthenticationProvider.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for the AuthenticationManager used to map the request to the correct auth provider.
     * @param authenticationConfiguration the auth configuration.
     * @return the configured auth manager instance.
     * @throws Exception if errors occur when retrieving the auth manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

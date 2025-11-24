package com.ajackus.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)                                      // JWT = no CSRF
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))          // H2 Console needs this
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()                 // H2 Console (MUST be first!)
                        .requestMatchers("/api/auth/**").permitAll()                   // Login, register, etc.
                        .requestMatchers("/api/quiz/**").permitAll()                   // Public quiz access
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")   // Admin only
                        .anyRequest().authenticated()                                  // Everything else needs JWT
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}


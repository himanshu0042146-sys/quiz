package com.ajackus.quiz.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .authorities("ROLE_ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password(encoder.encode("user123"))
                .authorities("ROLE_USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

}


package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails java = User.withUsername("java")
                .password("{noop}password")
                .authorities("read", "write", "ROLE_admin", "ROLE_user")
                .build();

        UserDetails rush = User.withUsername("rush")
                .password("{noop}password")
                .authorities("read", "ROLE_user")
                .build();

        return new InMemoryUserDetailsManager(java, rush);
    }
}

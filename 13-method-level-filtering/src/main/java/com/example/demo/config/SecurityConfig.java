package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.httpBasic(Customizer.withDefaults());
    http.authorizeHttpRequests(authz -> authz.anyRequest().authenticated());
    return http.build();
  }

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

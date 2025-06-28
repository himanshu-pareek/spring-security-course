package dev.javarush.spring_security.filters_configurations.security;

import dev.javarush.spring_security.filters_configurations.security.filters.LoggingFilter;
import dev.javarush.spring_security.filters_configurations.security.filters.StaticKeyAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new LoggingFilter(), SecurityContextHolderFilter.class);
        http.addFilterAfter(new LoggingFilter(), AuthorizationFilter.class);
        http.addFilterAt(new StaticKeyAuthenticationFilter(), AuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());
//        http.authorizeHttpRequests(
//                authz -> authz.anyRequest().authenticated()
//        );
        http.authorizeHttpRequests(
                authz -> authz.anyRequest().permitAll()
        );
        return http.build();
    }
}

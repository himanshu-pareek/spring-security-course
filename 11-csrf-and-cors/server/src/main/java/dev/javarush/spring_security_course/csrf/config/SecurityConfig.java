package dev.javarush.spring_security_course.csrf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.formLogin(Customizer.withDefaults());

        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(HttpMethod.GET, "/hello").permitAll()
                .anyRequest().authenticated());

        return http.build();
    }

}

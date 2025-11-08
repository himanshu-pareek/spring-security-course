package dev.javarush.spring_security_course.select_request_for_auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.httpBasic(Customizer.withDefaults());

    http.csrf(AbstractHttpConfigurer::disable);

    http.authorizeHttpRequests(authz ->
        authz
            // .requestMatchers(HttpMethod.POST).hasRole("admin") // hasAuthority("ROLE_admin")
            // .requestMatchers(HttpMethod.POST, "/books").hasRole("admin")
//            .requestMatchers("/books").hasRole("admin")
//            .requestMatchers("/books/*").hasAuthority("books.read") // /books/10, /books/10/authors (x)
//            .requestMatchers("/books/**").hasAuthority("books.read") // /books/10, /books/10/authors, ...
//            .requestMatchers("/books/{bookId:^[0-9]*$}").hasAuthority("books.edit")
            .requestMatchers(new RegexRequestMatcher("/books/[0-9]*", "GET")).hasAuthority("ROLE_admin")
            .anyRequest().authenticated()
    );

    return http.build();
  }

  @Bean
  UserDetailsService userDetailsService() {
    UserDetails java = User.withUsername("java")
        .password("{noop}password")
        .authorities("ROLE_user", "ROLE_admin", "books.delete", "books.edit")
        .build();
    UserDetails rush = User.withUsername("rush")
        .password("{noop}password")
        .authorities("ROLE_user", "books.read")
        .build();
    return new InMemoryUserDetailsManager(java, rush);
  }
}

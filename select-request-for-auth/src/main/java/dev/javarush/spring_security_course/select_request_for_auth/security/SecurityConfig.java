package dev.javarush.spring_security_course.select_request_for_auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    http.authorizeHttpRequests(authz ->
        authz
//        1. Deny all
//        authz.anyRequest().denyAll()
//
//        2. Permit all
//        authz.anyRequest().permitAll()

            // Only matches /books
          // .requestMatchers("/books").hasAuthority("ROLE_admin")

            // Matchs /books/1, /books/1bc and not /books/10/authors
            // .requestMatchers("/books/*").hasAuthority("books.edit")

            // Matches /books/1, /books/10/authors, /books/abc/authors/98
            // .requestMatchers("/books/**").hasAuthority("books.read")

            // Matches /books/1, /books/abc, not /books/abc/def
            // .requestMatchers("/books/{bookId}").hasAuthority("books.read")

            // Matches /books/10, /books/1, not /books/a, /books/a1, /books/1a
            // .requestMatchers("/books/{bookId:^[0-9]*$}").hasAuthority("ROLE_admin")

            // Matches /books/10, /books/1, not /books/a, /books/a1, /books/1a
            .requestMatchers(new RegexRequestMatcher("/books/[0-9]*", "GET")).hasAuthority("ROLE_admin")
            .anyRequest().authenticated()

    );

    return http.build();
  }

  @Bean
  UserDetailsService userDetailsService() {
    UserDetails java = User.withUsername("java")
        .password("{noop}password")
        .authorities("ROLE_user", "ROLE_admin", "books.delete", "books.edit", "books.read")
        .build();
    UserDetails rush = User.withUsername("rush")
        .password("{noop}password")
        .authorities("ROLE_user", "books.read")
        .build();
    return new InMemoryUserDetailsManager(java, rush);
  }
}

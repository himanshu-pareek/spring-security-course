package dev.javarush.spring_security.permission_based_authorization.security;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic(Customizer.withDefaults());

    http.authorizeHttpRequests(authz ->
//        authz.anyRequest().hasAuthority("books.edit")
//        authz.anyRequest().hasAnyAuthority("books.edit", "ROLE_admin")
//        authz.anyRequest().hasRole("admin")
//        authz.anyRequest().hasAnyRole("admin", "manager")
//        authz.anyRequest().authenticated()
//        authz.anyRequest().permitAll()
        authz.anyRequest().access(new AuthorizationManager<RequestAuthorizationContext>() {
          @Override
          public AuthorizationDecision check(Supplier<Authentication> authentication,
                                             RequestAuthorizationContext object) {
            Authentication auth = authentication.get();
            if (auth.isAuthenticated()) {
              Set<String> authorities =
                  auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                      .collect(Collectors.toSet());
              if (authorities.contains("books.edit") && authorities.contains("ROLE_admin")) {
                return new AuthorizationDecision(true);
              }
            }
            return new AuthorizationDecision(false);
          }
        })
    );

    return http.build();
  }

  @Bean
  UserDetailsService userDetailsService() {
    UserDetails java = User.withUsername("java")
        .password("{noop}password")
        .authorities("books.read", "books.edit", "ROLE_admin")
        .build();
    UserDetails rush = User.withUsername("rush")
        .password("{noop}password")
        .authorities("books.read", "books.edit", "ROLE_user")
        .build();
    UserDetails admin = User.withUsername("admin")
        .password("{noop}password")
        .authorities("books.read", "ROLE_admin", "ROLE_user")
        .build();
    return new InMemoryUserDetailsManager(java, rush, admin);
  }
}

package dev.javarush.spring_security_course.csrf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic(Customizer.withDefaults());
    http.formLogin(form ->
        form
            .loginPage("/login")
    );
    http.csrf(csrf ->
        csrf
            .csrfTokenRepository(new InMemoryCsrfTokenRepository())
    );
    http.authorizeHttpRequests(authz ->
        authz
            .requestMatchers("/login").permitAll()
            .anyRequest().authenticated()
    );

    // Filters
    http.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class);

    return http.build();
  }

  @Bean
  UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user")
        .password("{noop}password")
        .authorities("ROLE_user")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
}

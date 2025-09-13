package dev.javarush.spring_security.security_context;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableAsync
public class SecurityConfig {
  @Bean
  UserDetailsService userDetailsService() {
    UserDetails java =
        User.withUsername("java").password("{noop}password").authorities("ROLE_USER", "ROLE_ADMIN")
            .build();
    UserDetails rush =
        User.withUsername("rush").password("{noop}password").authorities("ROLE_USER")
            .build();
    return new InMemoryUserDetailsManager(java, rush);
  }

  @Bean
  InitializingBean initializingBean() {
    return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
  }
}

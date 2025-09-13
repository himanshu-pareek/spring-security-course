package dev.javarush.spring_security.form_configuration.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(form -> {
//      form.defaultSuccessUrl("/hello", true);
//      form.successHandler(new AuthenticationSuccessHandler() {
//        @Override
//        public void onAuthenticationSuccess(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            Authentication authentication)
//            throws IOException, ServletException {
//          response.addHeader("X-username", authentication.getName());
//        }
//      });
//
//      form.failureHandler(new AuthenticationFailureHandler() {
//        @Override
//        public void onAuthenticationFailure(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            AuthenticationException exception)
//            throws IOException, ServletException {
//          response.addHeader("X-auth-error", exception.getLocalizedMessage());
//        }
//      });

      form.loginPage("/login");

      form.usernameParameter("email");
    });

    http.authorizeHttpRequests(authz ->
        authz.requestMatchers("/login", "/login.html", "/error").permitAll()
            .anyRequest().authenticated());

    http.csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }
}

package dev.javarush.spring_security.auth_provider_demo.security.auth_provider;

import java.util.List;
import java.util.Objects;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class MyAuthProvider implements AuthenticationProvider {
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    if (!Objects.equals(username, "admin")) {
      return null;
    }
    Object password = authentication.getCredentials();
    if (!Objects.equals(password, "@dmin")) {
      throw new BadCredentialsException("Incorrect username or password");
    }
    return new UsernamePasswordAuthenticationToken(
        authentication.getPrincipal(),
        null,
        List.of(
            new SimpleGrantedAuthority("ADMIN"),
            new SimpleGrantedAuthority("USER")
        )
    );
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}

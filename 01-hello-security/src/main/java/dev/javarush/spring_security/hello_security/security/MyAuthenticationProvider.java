package dev.javarush.spring_security.hello_security.security;

import org.apache.catalina.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Objects;

public class MyAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Object password = authentication.getCredentials();
        if (Objects.equals(username, "java") && Objects.equals(password, "rush")) {
            return new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    null,
                    List.of(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN"))
            );
        }
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

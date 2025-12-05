package com.example.resource_server.security;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.oauth2ResourceServer(
        rs ->
            rs
//                .jwt(Customizer.withDefaults())
//                .jwt(
//                    jwt -> jwt.jwtAuthenticationConverter(new MyJwtConverter())
//                )
//                .opaqueToken(Customizer.withDefaults())
                .opaqueToken(
                    opaque ->
                        opaque.authenticationConverter(
                            new OpaqueTokenAuthenticationConverter() {
                              @Override
                              public Authentication convert(String introspectedToken,
                                                            OAuth2AuthenticatedPrincipal authenticatedPrincipal) {
                                Instant iat = authenticatedPrincipal.getAttribute(
                                    OAuth2TokenIntrospectionClaimNames.IAT);
                                Instant exp = authenticatedPrincipal.getAttribute(
                                    OAuth2TokenIntrospectionClaimNames.EXP);
                                OAuth2AccessToken accessToken =
                                    new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                                        introspectedToken,
                                        iat, exp);
                                Object rolesClaim = authenticatedPrincipal.getAttribute("roles");
                                if (rolesClaim instanceof List<?> roles) {
                                  if (!roles.isEmpty()) {
                                    List<SimpleGrantedAuthority> extendedAuthorities =
                                        roles.stream()
                                            .filter(role -> role instanceof String)
                                            .map(role -> "ROLE_" + role)
                                            .map(SimpleGrantedAuthority::new)
                                            .toList();
                                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                                    authorities.addAll(authenticatedPrincipal.getAuthorities());
                                    authorities.addAll(extendedAuthorities);
                                    return new BearerTokenAuthentication(authenticatedPrincipal,
                                        accessToken, authorities);
                                  }
                                }
                                return new BearerTokenAuthentication(authenticatedPrincipal,
                                    accessToken,
                                    authenticatedPrincipal.getAuthorities());
                              }
                            }
                        )
                )
    );
    http.authorizeHttpRequests(authz -> authz
        .requestMatchers(HttpMethod.GET, "/books").hasAuthority("SCOPE_books.list")
        .requestMatchers(HttpMethod.GET, "/books/*").hasAuthority("SCOPE_books.read")
        .requestMatchers(HttpMethod.POST, "/books").hasAuthority("SCOPE_books.edit")
        .anyRequest().authenticated()
    );
    return http.build();
  }

  class MyJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtAuthenticationConverter delegate;

    MyJwtConverter() {
      this.delegate = new JwtAuthenticationConverter();
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
      var authentication = this.delegate.convert(jwt);
      Object rolesClaim = jwt.getClaims().get("roles");
      if (rolesClaim instanceof List<?> roles) {
        if (!roles.isEmpty()) {
          List<SimpleGrantedAuthority> extendedAuthorities = roles.stream()
              .filter(role -> role instanceof String)
              .map(role -> "ROLE_" + role)
              .map(SimpleGrantedAuthority::new)
              .toList();
          Collection<GrantedAuthority> authorities = new ArrayList<>();
          authorities.addAll(authentication.getAuthorities());
          authorities.addAll(extendedAuthorities);
          return new JwtAuthenticationToken(jwt, authorities, authentication.getName());
        }
      }
      return authentication;
    }
  }
}

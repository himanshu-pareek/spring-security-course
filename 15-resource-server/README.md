# OAuth2 Resource Server (API Server)

> Look at the References to learn more about OAuth2

## Prerequisites

* Watch previous video (OAuth2 Authorization Server)
* Basic understanding of **OAuth2 Authorization** along with **OpenID Connect Protocol**
* Basic understanding of roles in OAuth2:
  * Authorization Server
  * Client / Application
  * Resource Server / API Server
  * Resource Owner / User
* Basic understanding of different formats of Access Token
  * JWT
  * Opaque
* Basic understanding of OAuth2 flows
  * Authorization Code Grant Flow
  * Client Credentials Flow
  * Token Introspection

## Resource(API) Server

* Provides interface to access resources
* Validates Access Token
  * JWT (Self Contained) using public key
  * OPAQUE (Reference) asking Authorization Server

## Minimal Configuration

### Configuration ⚙️

```
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080
```

### At startup 🚀

1. Query the provider configuration for `jwks_url` property
2. Query `jwks_url` for supported algorithms
3. Configuration the validation strategy

### At runtime 🏃

1. Validate the JWT using public keys obtained from `jwks_url`
2. Validate `iss`, `nbf` and `exp`
3. Scope to Authority (`SCOPE_` prefix)

## Customized JWT

1. Authorization server adds custom claims to the access token
2. Resource server reads the custom claims and stores in Authentication
3. Use the custom claim wherever required

[`SecurityConfig.java`](http://SecurityConfig.java)  (Authorization Server)

```java
@Bean
OAuth2TokenCustomizer<JwtEncodingContext> jwtAccessTokenCustomizer() {
    return new OAuth2TokenCustomizer<JwtEncodingContext>() {
      @Override
      public void customize(JwtEncodingContext context) {
        JwtClaimsSet.Builder claims = context.getClaims();
        if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
	        // If the token type is access token
          Authentication authentication = context.getPrincipal();
          // Do something based on authentication
          // Like, get all roles and put in `roles` claim
          claims.claim("roles", roles);
        }
      }
    };
  }
```

[`SecurityConfig.java`](http://SecurityConfig.java) (Resource Server)

```java
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(
            authz -> authz.anyRequest().authenticated()
        )
        .oauth2ResourceServer(
            oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(new MyJwtConverter()))
        );
    return http.build();
  }
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

```

## Opaque Token

### Authorization Server

```java
// 1. Use opaque access token for the client
RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
				// other configuration
        .tokenSettings(TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.REFERENCE).accessTokenTimeToLive(Duration.ofHours(4)).build())
				.build();
				
// 2. Create a client for resource server (for token introspection)
RegisteredClient resourceServer = RegisteredClient.withId(UUID.randomUUID().toString())
            // other minimal configuration
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .build();
```

### Resource Server

```java
spring.security.oauth2.resourceserver.opaquetoken.introspection-uri=http://localhost:8080/oauth2/introspect
spring.security.oauth2.resourceserver.opaquetoken.client-id=resource-server
spring.security.oauth2.resourceserver.opaquetoken.client-secret=password-res
```

## Customized Opaque Token

### Authorization Server

```java
@Bean
OAuth2TokenCustomizer<OAuth2TokenClaimsContext> opaqueTokenCustomizer() {
  return new OAuth2TokenCustomizer<OAuth2TokenClaimsContext>() {
    @Override
    public void customize(OAuth2TokenClaimsContext context) {
      List<String> roles = context.getPrincipal().getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .filter(a -> a.startsWith("ROLE_"))
            .map(a -> a.substring(5))
            .toList();
        context.getClaims().claim("roles", roles);
    }
  };
}
```

### Resource Server

```java
http
  .authorizeHttpRequests(
      authz -> authz.anyRequest().authenticated()
  )
  .oauth2ResourceServer(
      oauth2 -> oauth2
          .opaqueToken(opaque -> opaque
              .authenticationConverter(new OpaqueTokenAuthenticationConverter() {
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
                            List<SimpleGrantedAuthority> extendedAuthorities = roles.stream()
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
                        return new BearerTokenAuthentication(authenticatedPrincipal, accessToken,
                            authenticatedPrincipal.getAuthorities());
                }
              }))
  );
return http.build();
```

## Reference
* https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html
* https://www.rfc-editor.org/rfc/rfc6749
* https://oauth.net/2/
* https://openid.net/developers/how-connect-works/
* https://www.youtube.com/playlist?list=PLbk-lC-jmHejz7ZTfOqFyXH15yt6b3WHe
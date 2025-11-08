package dev.javarush.spring_security_course.csrf.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

// X-identifier -> Identifier for the request
class InMemoryCsrfTokenRepository implements CsrfTokenRepository {
  private final Map<String, CsrfToken> tokens;

  InMemoryCsrfTokenRepository() {
    this.tokens = new ConcurrentHashMap<>();
  }

  @Override
  public CsrfToken generateToken(HttpServletRequest request) {
    UUID token = UUID.randomUUID();
    return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.toString());
  }

  @Override
  public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
    String identifier = request.getHeader("X-Identifier");
    System.out.println("Saving token for " + identifier);
    this.tokens.put(identifier, token);
  }

  @Override
  public CsrfToken loadToken(HttpServletRequest request) {
    String identifier = request.getHeader("X-Identifier");
    System.out.println("Loading token for " + identifier);
    return this.tokens.get(identifier);
  }
}

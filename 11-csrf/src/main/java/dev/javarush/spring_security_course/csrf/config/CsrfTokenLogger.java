package dev.javarush.spring_security_course.csrf.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.security.web.csrf.CsrfToken;

public class CsrfTokenLogger implements Filter {
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    var req = (HttpServletRequest) servletRequest;
    var csrf = (CsrfToken) req.getAttribute("_csrf");
    System.out.println(csrf.getToken());

    filterChain.doFilter(servletRequest, servletResponse);
  }
}

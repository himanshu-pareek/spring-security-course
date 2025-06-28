package dev.javarush.spring_security.filters_configurations.security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        log.info("Incoming request {}", request.getPathInfo());
        filterChain.doFilter(servletRequest, servletResponse);
        var response = (HttpServletResponse) servletResponse;
        log.info("Outgoing response {}", response.getStatus());
    }
}

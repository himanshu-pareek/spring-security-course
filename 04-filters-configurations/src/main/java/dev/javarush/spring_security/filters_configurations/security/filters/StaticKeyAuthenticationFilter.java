package dev.javarush.spring_security.filters_configurations.security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

public class StaticKeyAuthenticationFilter implements Filter {

    private static final String KEY = "abcd";
    public static final String KEY_HEADER = "X-req-key";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        String key = req.getHeader(KEY_HEADER);
        if (key == null || !Objects.equals(KEY, key)) {
            var res = (HttpServletResponse) response;
            res.setStatus(403);
            return;
        }
        chain.doFilter(request, response);
    }
}

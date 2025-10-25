package com.devops.microservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-Parse-REST-API-Key";
    private static final String VALID_API_KEY = "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String apiKey = request.getHeader(API_KEY_HEADER);

        if (VALID_API_KEY.equals(apiKey)) {
            SecurityContextHolder.getContext().setAuthentication(new ApiKeyAuthentication());
        }

        filterChain.doFilter(request, response);
    }
}
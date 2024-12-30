package org.practice.cartalert.auth.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException e) {
            log.error("JWT Authentication Exception: {}", e.getMessage());
            setErrorResponse(response, e);
        } catch (Exception e) {
            log.error("Unexpected Exception: {}", e.getMessage());
            setErrorResponse(response, new JwtAuthenticationException("Internal server error"));
        }
    }

    private void setErrorResponse(HttpServletResponse response, JwtAuthenticationException e)
            throws IOException {
        response.setStatus(e.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", e.getStatus().value());
        errorDetails.put("error", e.getStatus().getReasonPhrase());
        errorDetails.put("message", e.getMessage());
        errorDetails.put("path", e.getPath());
        errorDetails.put("timestamp", System.currentTimeMillis());

        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
}
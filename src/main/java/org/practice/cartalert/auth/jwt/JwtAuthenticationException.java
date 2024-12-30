package org.practice.cartalert.auth.jwt;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtAuthenticationException extends RuntimeException {

    private final HttpStatus status;
    private final String path;

    public JwtAuthenticationException(String message) {
        this(message, HttpStatus.UNAUTHORIZED, "/");
    }

    public JwtAuthenticationException(String message, HttpStatus status, String path) {
        super(message);
        this.status = status;
        this.path = path;
    }
}
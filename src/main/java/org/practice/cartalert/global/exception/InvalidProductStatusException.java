package org.practice.cartalert.global.exception;

public class InvalidProductStatusException extends RuntimeException {
    public InvalidProductStatusException(String message) {
        super(message);
    }
}
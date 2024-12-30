package org.practice.cartalert.global.exception;

public class DuplicateEmailException extends RuntimeException{

    public DuplicateEmailException(String message) {
        super(message);
    }
}

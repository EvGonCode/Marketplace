package com.example.microserviceone.exception;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String message) {
        super("User %s already exist".formatted(message));
    }
}

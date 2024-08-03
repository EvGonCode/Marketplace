package com.example.microserviceone.exception;

public class NoSuchTagException extends RuntimeException{

    public NoSuchTagException(String message) {
        super("Tag %s doesn't exist".formatted(message));
    }
}

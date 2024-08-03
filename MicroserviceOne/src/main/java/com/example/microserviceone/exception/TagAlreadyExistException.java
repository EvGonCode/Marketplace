package com.example.microserviceone.exception;

public class TagAlreadyExistException extends RuntimeException{

    public TagAlreadyExistException(String message) {
        super("Tag %s already exist".formatted(message));
    }
}

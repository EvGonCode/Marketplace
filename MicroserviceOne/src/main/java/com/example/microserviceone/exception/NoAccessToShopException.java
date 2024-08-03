package com.example.microserviceone.exception;

public class NoAccessToShopException extends RuntimeException{
    public NoAccessToShopException(String message) {
        super("You have no authorities to edit \"%s\" shop".formatted(message));
    }
}

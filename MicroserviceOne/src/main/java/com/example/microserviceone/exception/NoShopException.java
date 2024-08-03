package com.example.microserviceone.exception;

public class NoShopException extends RuntimeException{
    public NoShopException(String message) {
        super("Shop %s doesn't exist".formatted(message));
    }
}

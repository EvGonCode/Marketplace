package com.example.microserviceone.exception;

public class ShopAddedToNotSellerException extends RuntimeException{
    public ShopAddedToNotSellerException(String message) {
        super("User %s is not seller. Shop can only be added to User with Role SELLER".formatted(message));
    }
}

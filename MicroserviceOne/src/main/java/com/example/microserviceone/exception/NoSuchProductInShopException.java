package com.example.microserviceone.exception;

public class NoSuchProductInShopException extends RuntimeException{

    public NoSuchProductInShopException(String shopName, String productNama) {
        super("Shop \"%s\" doesn't have product \"%s\"".formatted(shopName, productNama));
    }
}

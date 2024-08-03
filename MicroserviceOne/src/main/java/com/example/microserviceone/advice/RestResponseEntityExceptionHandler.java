package com.example.microserviceone.advice;

import com.example.microserviceone.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicateUserException.class)
    protected ResponseEntity<Object> handleDuplicateUser(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NoShopException.class)
    protected ResponseEntity<Object> handleShopDoesNotExist(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NoAccessToShopException.class)
    protected ResponseEntity<Object> handleNoAccessToShop(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchProductInShopException.class)
    protected ResponseEntity<Object> handleNoSuchProductInShop(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchTagException.class)
    protected ResponseEntity<Object> handleNoSuchTag(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchUserException.class)
    protected ResponseEntity<Object> handleNoSuchUser(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ShopAddedToNotSellerException.class)
    protected ResponseEntity<Object> handleShopAddedToNotSeller(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(TagAlreadyExistException.class)
    protected ResponseEntity<Object> handleTagAlreadyExist(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnexpectedException(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


}

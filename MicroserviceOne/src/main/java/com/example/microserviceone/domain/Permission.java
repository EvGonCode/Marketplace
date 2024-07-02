package com.example.microserviceone.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_ALL("admin:all"),
    SELLER_READ("management:read"),
    SELLER_UPDATE("management:update"),
    SELLER_CREATE("management:create"),
    SELLER_DELETE("management:delete")

    ;

    @Getter
    private final String permission;
}

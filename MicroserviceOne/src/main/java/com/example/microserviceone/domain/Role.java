package com.example.microserviceone.domain;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.microserviceone.domain.Permission.ADMIN_ALL;
import static com.example.microserviceone.domain.Permission.SELLER_CREATE;
import static com.example.microserviceone.domain.Permission.SELLER_DELETE;
import static com.example.microserviceone.domain.Permission.SELLER_READ;
import static com.example.microserviceone.domain.Permission.SELLER_UPDATE;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_ALL,
                    SELLER_READ,
                    SELLER_UPDATE,
                    SELLER_DELETE,
                    SELLER_CREATE
            )
    ),
    SELLER(
            Set.of(
                    SELLER_READ,
                    SELLER_UPDATE,
                    SELLER_DELETE,
                    SELLER_CREATE
            )
    ),
    MANAGER(
            Set.of(
                    SELLER_READ,
                    SELLER_UPDATE
            )
    )

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
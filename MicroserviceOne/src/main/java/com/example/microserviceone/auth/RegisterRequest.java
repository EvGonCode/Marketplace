package com.example.microserviceone.auth;

import com.example.microserviceone.domain.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterRequest {
    private String login;
    private String password;
}

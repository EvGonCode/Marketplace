package com.example.microserviceone.auth;

import com.example.microserviceone.config.JwtService;
import com.example.microserviceone.config.MyUserDetails;
import com.example.microserviceone.domain.Role;
import com.example.microserviceone.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import com.example.microserviceone.domain.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER.name())
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(new MyUserDetails(user));
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(), request.getPassword()
        ));
        var user = userRepo.findByLogin(request.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(new MyUserDetails(user));
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}

package com.example.microserviceone.auth;


import com.example.microserviceone.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/registerUser")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request, Role.USER));
    }

    @PostMapping("/registerSeller")
    public ResponseEntity<AuthenticationResponse> registerSeller(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request, Role.SELLER));
    }

    @PostMapping("/registerManager")
    public ResponseEntity<AuthenticationResponse> registerManager(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request, Role.MANAGER));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request, Role.ADMIN));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/unauthenticate")
    public String logout(@RequestBody JwtRequest jwtRequest) {
        return service.logout(jwtRequest.getJwt());
    }
}

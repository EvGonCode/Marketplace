package com.example.microserviceone.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.microserviceone.domain.Role.SELLER;
import static com.example.microserviceone.domain.Role.MANAGER;
import static com.example.microserviceone.domain.Role.ADMIN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/authenticate",
            "/registerUser",
            "/products",
            "/shops",
            "/tags"
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers(
                                        "/users",
                                        "/registerSeller",
                                        "/registerAdmin",
                                        "/new-shop/{owner_id}",
                                        "/new-tag").hasAnyRole(ADMIN.name())
                                .requestMatchers(
                                        "/registerManager",
                                        "/new-shop").hasAnyRole(ADMIN.name(), SELLER.name())
                                .requestMatchers(
                                        "/new-product/{shopName}",
                                        "/product/new-tag").hasAnyRole(ADMIN.name(), SELLER.name(), MANAGER.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }


}

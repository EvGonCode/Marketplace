package com.example.microserviceone.controllers;

import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/shops")
    public List<ShopDto> index(){
        return shopService.findAll().stream().map(ShopDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/new-shop/{ownerName}")
    public ResponseEntity addShopAdmin(@RequestBody ShopDto shopDto, @PathVariable String ownerName) {
        shopService.addShopAdmin(shopDto, ownerName);
        return ResponseEntity.ok("Shop is saved");
    }

    @PostMapping("/new-shop")
    public ResponseEntity addShop(@RequestBody ShopDto shopDto, Authentication authentication) {
        shopService.addShop(shopDto, authentication);
        return ResponseEntity.ok("Shop is saved");
    }
}

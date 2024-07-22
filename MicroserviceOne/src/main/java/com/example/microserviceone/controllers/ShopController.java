package com.example.microserviceone.controllers;

import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.services.ShopService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/new-shop/{owner_id}")
    public String addShopAdmin(@RequestBody ShopDto shopDto, @PathVariable Integer owner_id) {
        if (shopService.addShopAdmin(shopDto, owner_id)) {
            return "Shop is saved";
        }
        else return "Shop creation error";
    }

    @PostMapping("/new-shop")
    public String addShop(@RequestBody ShopDto shopDto, Authentication authentication) {
        if(shopService.addShop(shopDto, authentication)) {
            return "Shop is saved";
        }
        else return "Shop creation error";
    }
}

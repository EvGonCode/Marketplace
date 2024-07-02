package com.example.microserviceone.controllers;

import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.services.ShopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/shops")
    public List<ShopDto> index(){
        return shopService.findAll().stream().map(ShopDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/new-shop/{ownerId}")
    public String addShop(@RequestBody ShopDto shopDto, @PathVariable Integer ownerId) {

        shopService.addShop(shopDto, ownerId);
        return "Shop is saved";
    }
}

package com.example.microserviceone.controllers;

import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ShopController {
    @Autowired
    ShopService shopService;

    @GetMapping("/shops")
    public List<ShopDto> index(){
        return shopService.findAll().stream().map(ShopDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/new-shop/{owner_id}")
    public String addShop(@RequestBody ShopDto shopDto, @PathVariable Integer owner_id) {

        shopService.addShop(shopDto, owner_id);
        return "Shop is saved";
    }
}

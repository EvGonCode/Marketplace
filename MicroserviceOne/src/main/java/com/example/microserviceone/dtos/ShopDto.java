package com.example.microserviceone.dtos;

import com.example.microserviceone.domain.Shop;

import java.util.List;
import java.util.stream.Collectors;

public record ShopDto(String name, List<UserDto> managers){

    public static ShopDto toDto(Shop shop){
        ShopDto shopDto = new ShopDto(shop.getName(), shop.getManagers().stream().map(UserDto::toDto)
                .collect(Collectors.toList()));
        return shopDto;
    }
}

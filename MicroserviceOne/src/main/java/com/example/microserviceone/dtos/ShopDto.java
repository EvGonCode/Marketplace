package com.example.microserviceone.dtos;

import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

public record ShopDto(String name, UserDto owner){

    public static ShopDto toDto(Shop shop){
        ShopDto shopDto = new ShopDto(shop.getName(), UserDto.toDto(shop.getOwner()));
        return shopDto;
    }
}

package com.example.microserviceone.services;

import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.dtos.ShopDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ShopService {
    List<Shop> findAll();

    List<Shop> findByOwner(User owner);

    void addShopAdmin(ShopDto shopDto, String ownerName);

    void addShop(ShopDto shopDto, Authentication authentication);

    void editShopValidation(Authentication authentication, String shopName);

}

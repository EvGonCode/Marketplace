package com.example.microserviceone.services;

import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.dtos.ShopDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ShopService {
    List<Shop> findAll();

    List<Shop> findByOwner(User owner);

    boolean addShopAdmin(ShopDto shopDto, Integer ownerId);

    boolean addShop(ShopDto shopDto, Authentication authentication);

    boolean editShopValidation(Authentication authentication, String shopName);

}

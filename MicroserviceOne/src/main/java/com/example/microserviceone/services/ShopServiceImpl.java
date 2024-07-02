package com.example.microserviceone.services;

import com.example.microserviceone.config.MyUserDetails;
import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.dtos.TagDto;
import com.example.microserviceone.repositories.ShopRepo;
import com.example.microserviceone.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService{
    @Autowired
    private final ShopRepo shopRepo;
    @Autowired
    private final UserRepo userRepo;

    @Override
    public List<Shop> findAll() {
        return shopRepo.findAll();
    }

    @Override
    public void addShop(ShopDto shopDto, Integer ownerId) {
        Shop shop = new Shop(shopDto.name());
        shop.setOwner(userRepo.findById(ownerId).get());
        shopRepo.save(shop);
    }

    public List<Shop> findByOwner(User owner){
        return shopRepo.findByOwner(owner);
    }

    @Override
    public boolean editShopValidation(Authentication authentication, Integer shopId) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User requestOwner = userRepo.findById(userDetails.getUserId()).get();
        Shop shop = shopRepo.findById(shopId).get();
        return shop.getOwner().getId() == requestOwner.getId();

    }
}

package com.example.microserviceone.services;

import com.example.microserviceone.config.MyUserDetails;
import com.example.microserviceone.domain.Role;
import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.repositories.ShopRepo;
import com.example.microserviceone.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService{
    private final ShopRepo shopRepo;
    private final UserRepo userRepo;

    @Override
    public List<Shop> findAll() {
        return shopRepo.findAll();
    }

    @Override
    public boolean addShopAdmin(ShopDto shopDto, Integer ownerId) {
        User owner = userRepo.findById(ownerId).get();
        if(owner.getRole() == Role.SELLER) {
            Shop shop = new Shop(shopDto.name());
            shop.getManagers().add(owner);
            owner.getShops().add(shop);
            userRepo.save(owner);
            return true;
        }
        return false;
    }

    public boolean addShop(ShopDto shopDto, Authentication authentication){
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User requestOwner = userRepo.findById(userDetails.getUserId()).get();
        Shop shop = new Shop(shopDto.name());
        shop.getManagers().add(requestOwner);
        requestOwner.getShops().add(shop);
        userRepo.save(requestOwner);
        //shopRepo.save(shop);
        return true;
    }

    public List<Shop> findByOwner(User owner){
        List<Shop> result = new LinkedList<>();
        if(owner.getRole() != Role.SELLER){ return result;}
        return (List<Shop>) owner.getShops();
    }

    @Override
    public boolean editShopValidation(Authentication authentication, String shopName) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User requestOwner = userRepo.findById(userDetails.getUserId()).get();
        if(requestOwner.getRole().equals(Role.ADMIN)) return true;

        Set<User> managers = shopRepo.findByName(shopName).get().getManagers();
        return managers.stream().anyMatch(user -> user.getId().equals(requestOwner.getId()));
    }
}

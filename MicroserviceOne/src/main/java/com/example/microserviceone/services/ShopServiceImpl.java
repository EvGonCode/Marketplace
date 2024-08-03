package com.example.microserviceone.services;

import com.example.microserviceone.config.MyUserDetails;
import com.example.microserviceone.domain.Role;
import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.exception.NoAccessToShopException;
import com.example.microserviceone.exception.NoShopException;
import com.example.microserviceone.exception.NoSuchUserException;
import com.example.microserviceone.exception.ShopAddedToNotSellerException;
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
    public void addShopAdmin(ShopDto shopDto, String ownerName) {
        if(userRepo.findByLogin(ownerName).isEmpty()){
            throw new NoSuchUserException(ownerName);
        }
        User owner = userRepo.findByLogin(ownerName).get();
        if(owner.getRole() != Role.SELLER) {
            throw new ShopAddedToNotSellerException(ownerName);
        }
        Shop shop = new Shop(shopDto.name());
        shop.getManagers().add(owner);
        owner.getShops().add(shop);
        userRepo.save(owner);
    }

    public void addShop(ShopDto shopDto, Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User requestOwner = userRepo.findById(userDetails.getUserId()).get();
        Shop shop = new Shop(shopDto.name());
        shop.getManagers().add(requestOwner);
        requestOwner.getShops().add(shop);
        userRepo.save(requestOwner);
    }

    public List<Shop> findByOwner(User owner){
        List<Shop> result = new LinkedList<>();
        if(owner.getRole() != Role.SELLER){ return result;}
        return (List<Shop>) owner.getShops();
    }

    @Override
    public void editShopValidation(Authentication authentication, String shopName) {
        if(shopRepo.findByName(shopName).isEmpty()){
            throw new NoShopException(shopName);
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User requestOwner = userRepo.findById(userDetails.getUserId()).get();
        if(requestOwner.getRole().equals(Role.ADMIN)) return;

        Set<User> managers = shopRepo.findByName(shopName).get().getManagers();
        if (managers.stream().noneMatch(user -> user.getId().equals(requestOwner.getId()))) throw new NoAccessToShopException(shopName);
    }
}

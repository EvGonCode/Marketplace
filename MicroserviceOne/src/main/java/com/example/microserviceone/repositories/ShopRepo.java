package com.example.microserviceone.repositories;

import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ShopRepo extends JpaRepository<Shop, Integer> {
    Optional<Shop> findByName(String name);
}

package com.example.microserviceone.repositories;

import com.example.microserviceone.domain.Product;
import com.example.microserviceone.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
//    Optional<Product> findByProductId(Integer productId);
}

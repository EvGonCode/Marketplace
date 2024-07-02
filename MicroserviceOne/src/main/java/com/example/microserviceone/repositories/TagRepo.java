package com.example.microserviceone.repositories;

import com.example.microserviceone.domain.Product;
import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepo  extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);
//    Optional<Tag> findByTagId(Integer tagId);
}

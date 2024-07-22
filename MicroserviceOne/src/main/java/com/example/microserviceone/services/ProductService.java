package com.example.microserviceone.services;

import com.example.microserviceone.domain.Product;
import com.example.microserviceone.dtos.ProductDto;
import com.example.microserviceone.dtos.ProductTagDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Integer productId);
    void addProduct(ProductDto productDto, String shopName);
    void addTagToProduct(ProductTagDto ptd);
    boolean editProductValidation(Authentication authentication, Integer productId);

}

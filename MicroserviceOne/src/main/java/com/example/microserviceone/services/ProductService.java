package com.example.microserviceone.services;

import com.example.microserviceone.domain.Product;
import com.example.microserviceone.dtos.ProductDto;
import com.example.microserviceone.dtos.ProductTagDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Integer productId);
    Product findByName(String productName);
    void addProduct(ProductDto productDto, String shopName);
    void addTagToProduct(String productName, String shopName);
    void editProductValidation(Authentication authentication, String productName, String shopName);

}

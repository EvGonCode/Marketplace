package com.example.microserviceone.controllers;

import com.example.microserviceone.config.MyUserDetails;
import com.example.microserviceone.domain.Product;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.dtos.ProductDto;
import com.example.microserviceone.dtos.ProductTagDto;
import com.example.microserviceone.dtos.TagDto;
import com.example.microserviceone.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ShopService shopService;
    @Autowired
    UserService userService;

    @GetMapping("/products")
    public List<ProductDto> index(){
        return productService.findAll().stream().map(ProductDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/new-product/{shop_id}")
    public String addProduct(@RequestBody ProductDto productDto, @PathVariable Integer shop_id, Authentication authentication) {
        if(!shopService.editShopValidation(authentication, shop_id)){
            return "Access denied";
        }

        productService.addProduct(productDto, shop_id);
        return "Product is saved";
    }

    @PostMapping("/product/new-tag")
    public String addTagToProduct(@RequestBody ProductTagDto ptd) {
        productService.addTagToProduct(ptd);
        return "Tag is added to the product";
    }
}

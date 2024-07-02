package com.example.microserviceone.controllers;

import com.example.microserviceone.dtos.ProductDto;
import com.example.microserviceone.dtos.ProductTagDto;
import com.example.microserviceone.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ShopService shopService;

    public ProductController(ProductService productService, ShopService shopService) {
        this.productService = productService;
        this.shopService = shopService;
    }

    @GetMapping("/products")
    public List<ProductDto> index(){
        return productService.findAll().stream().map(ProductDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/new-product/{shopId}")
    public String addProduct(@RequestBody ProductDto productDto, @PathVariable Integer shopId, Authentication authentication) {
        if(!shopService.editShopValidation(authentication, shopId)){
            return "Access denied";
        }

        productService.addProduct(productDto, shopId);
        return "Product is saved";
    }

    @PostMapping("/product/new-tag")
    public String addTagToProduct(@RequestBody ProductTagDto ptd) {
        productService.addTagToProduct(ptd);
        return "Tag is added to the product";
    }
}

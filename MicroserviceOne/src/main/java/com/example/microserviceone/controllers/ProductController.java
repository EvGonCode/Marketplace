package com.example.microserviceone.controllers;

import com.example.microserviceone.dtos.ProductDto;
import com.example.microserviceone.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ShopService shopService;
    private final TagService tagService;

    @GetMapping("/products")
    public List<ProductDto> index(){
        return productService.findAll().stream().map(ProductDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/new-product/{shopName}")
    public ResponseEntity addProduct(@RequestBody ProductDto productDto, @PathVariable String shopName, Authentication authentication) {
        shopService.editShopValidation(authentication, shopName);
        productService.addProduct(productDto, shopName);
        return ResponseEntity.ok("Product is saved");
    }

    @PostMapping("/product/new-tag/{shopName}/{productName}/{tagName}")
    public ResponseEntity addTagToProduct(@PathVariable String shopName, @PathVariable String productName, @PathVariable String tagName, Authentication authentication) {
        productService.editProductValidation(authentication, productName, shopName);
        tagService.validateUsingTag(tagName);
        productService.addTagToProduct(productName, tagName);
        return ResponseEntity.ok("Tag is added to the product");
    }
}

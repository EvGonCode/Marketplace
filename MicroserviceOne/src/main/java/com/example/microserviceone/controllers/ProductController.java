package com.example.microserviceone.controllers;

import com.example.microserviceone.dtos.ProductDto;
import com.example.microserviceone.dtos.ProductTagDto;
import com.example.microserviceone.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ShopService shopService;

    @GetMapping("/products")
    public List<ProductDto> index(){
        return productService.findAll().stream().map(ProductDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/new-product/{shopName}")
    public String addProduct(@RequestBody ProductDto productDto, @PathVariable String shopName, Authentication authentication) {
        if(!shopService.editShopValidation(authentication, shopName)){
            return "Access to this shop is denied";
        }

        productService.addProduct(productDto, shopName);
        return "Product is saved";
    }

    @PostMapping("/product/new-tag")
    public String addTagToProduct(@RequestBody ProductTagDto ptd, Authentication authentication) {
        if(!productService.editProductValidation(authentication, ptd.productId())){
            return "Access to this shop is denied";
        }
        productService.addTagToProduct(ptd);
        return "Tag is added to the product";
    }
}

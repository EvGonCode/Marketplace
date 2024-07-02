package com.example.microserviceone.dtos;

import com.example.microserviceone.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public record ProductDto(String name, String description, Double price, List<TagDto> tags, ShopDto shop) {
    public static ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto(product.getName(), product.getDescription(), product.getPrice(),
                product.getTags().stream()
                        .map(TagDto::toDto)
                        .collect(Collectors.toList()),
                ShopDto.toDto(product.getShop()));
        return productDto;
    }
}


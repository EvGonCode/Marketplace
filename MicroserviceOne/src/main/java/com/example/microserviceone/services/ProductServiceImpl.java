package com.example.microserviceone.services;

import com.example.microserviceone.config.MyUserDetails;
import com.example.microserviceone.domain.Product;
import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.dtos.ProductDto;
import com.example.microserviceone.dtos.ProductTagDto;
import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.repositories.ProductRepo;
import com.example.microserviceone.repositories.ShopRepo;
import com.example.microserviceone.repositories.TagRepo;
import com.example.microserviceone.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepo productRepo;
    private final ShopRepo shopRepo;
    private final TagRepo tagRepo;
    private final UserRepo userRepo;

    public List<Product> findAll(){
        return productRepo.findAll();
    }

    public void addProduct(ProductDto pDto, Integer shopId){
        Product product = new Product(pDto.name(), pDto.description(), pDto.price());
        product.setShop(shopRepo.findById(shopId).get());
        Shop shop = shopRepo.findById(shopId).get();
        shop.getProducts().add(product);
        productRepo.save(product);
    }

    @Override
    public void addTagToProduct(ProductTagDto ptDto) {
        Product product = productRepo.findById(ptDto.productId()).get();
        product.getTags().add(tagRepo.findById(ptDto.tagId()).get());
        productRepo.save(product);
    }

    public Product findById(Integer productId){
        return productRepo.findById(productId).get();
    }
}

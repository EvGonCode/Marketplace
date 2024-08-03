package com.example.microserviceone.services;

import com.example.microserviceone.config.MyUserDetails;
import com.example.microserviceone.domain.Product;
import com.example.microserviceone.domain.Shop;
import com.example.microserviceone.domain.Tag;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.dtos.ProductDto;
import com.example.microserviceone.dtos.ProductTagDto;
import com.example.microserviceone.exception.NoSuchProductInShopException;
import com.example.microserviceone.repositories.ProductRepo;
import com.example.microserviceone.repositories.ShopRepo;
import com.example.microserviceone.repositories.TagRepo;
import com.example.microserviceone.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepo productRepo;
    private final ShopRepo shopRepo;
    private final TagRepo tagRepo;
    private final ShopService shopService;

    public List<Product> findAll(){
        return productRepo.findAll();
    }

    public void addProduct(ProductDto pDto, String shopName){
        Product product = new Product(pDto.name(), pDto.description(), pDto.price());
        Shop shop = shopRepo.findByName(shopName).get();
        product.setShop(shop);
        shop.getProducts().add(product);
        productRepo.save(product);
    }

    @Override
    public void addTagToProduct(String productName, String shopName) {
        Product product = productRepo.findByName(productName).get();
        Tag tag = tagRepo.findByName(shopName).get();
        tag.getProducts().add(product);
        product.getTags().add(tag);
        productRepo.save(product);
    }

    public void editProductValidation(Authentication authentication, String productName, String shopName){
        shopService.editShopValidation(authentication, shopName);
        Shop shop = shopRepo.findByName(shopName).get();
        List<String> shopProucts = shop.getProducts().stream().map(Product::getName).collect(Collectors.toList());
        if(!shopProucts.contains(productName)) {
           throw new NoSuchProductInShopException(shopName, productName);
        }
    }

    public Product findById(Integer productId){
        return productRepo.findById(productId).get();
    }

    public Product findByName(String productName){
        return productRepo.findByName(productName).get();
    }
}

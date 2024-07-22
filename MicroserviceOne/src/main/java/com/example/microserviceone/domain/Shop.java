package com.example.microserviceone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Shop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "shops")
    private Set<User> managers = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "shop")
    private Set<Product> products = new HashSet<>();


    public Shop(String name) {
        this.name = name;
    }

    public Shop() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer shopId) {
        this.id = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Set<User> getManagers() {
        return managers;
    }
}

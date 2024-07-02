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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private User owner;
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

    public User getOwner() {
        return owner;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}

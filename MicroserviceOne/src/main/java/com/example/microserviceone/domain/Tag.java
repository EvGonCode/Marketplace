package com.example.microserviceone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Tags")
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Product> products = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer tagId) {
        this.id = tagId;
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
}


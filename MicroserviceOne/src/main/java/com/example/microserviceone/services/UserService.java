package com.example.microserviceone.services;

import com.example.microserviceone.domain.User;

import java.util.List;

public interface UserService{
    List<User> findAll();

    User findById(Integer id);
}

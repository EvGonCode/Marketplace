package com.example.microserviceone.services;

import com.example.microserviceone.domain.User;

import java.util.List;

public interface UserService{
    List<User> findAll();
    void addUser(User user);

    User findById(Integer id);
}

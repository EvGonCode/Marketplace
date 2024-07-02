package com.example.microserviceone.controllers;

import com.example.microserviceone.domain.User;
import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.dtos.UserDto;
import com.example.microserviceone.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/users")
    public List<UserDto> index(){
        return userService.findAll().stream().map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/new-user")
    public String addUser(@RequestBody User user) {
        userService.addUser(user);
        return "User is saved";
    }

}
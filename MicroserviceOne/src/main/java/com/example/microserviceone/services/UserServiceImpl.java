package com.example.microserviceone.services;

import com.example.microserviceone.domain.User;
import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.dtos.UserDto;
import com.example.microserviceone.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired // kak v controller
    private  UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User findById(Integer id){
        return userRepo.findById(id).get();  // production ready ?
    }
}

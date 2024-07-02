package com.example.microserviceone.services;

import com.example.microserviceone.config.MyUserDetails;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired // kak v controller
    private UserRepo userRepo;

    @Override
    public MyUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByLogin(login);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(login + " not found"));
    }
}

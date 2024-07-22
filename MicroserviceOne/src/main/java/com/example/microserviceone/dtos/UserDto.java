package com.example.microserviceone.dtos;

import com.example.microserviceone.domain.User;

public record UserDto(String login, String role) {
    public static UserDto toDto(User user){
        UserDto userDto = new UserDto(user.getLogin(), user.getRole().name());
        return userDto;
    }
}

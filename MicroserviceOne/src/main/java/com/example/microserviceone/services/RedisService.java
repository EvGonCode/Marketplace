package com.example.microserviceone.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Boolean> redisTemplate;

    public void saveTokenRevokeData(String key, Boolean value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Boolean getTokenRevokeData(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}

package com.example.microserviceone.config;

import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisBooleanSerializer implements RedisSerializer<Boolean> {

    @Override
    public byte[] serialize(Boolean aBoolean) {
        return aBoolean != null ? (aBoolean ? "1" : "0").getBytes() : null;
    }

    @Override
    public Boolean deserialize(byte[] bytes) {
        return bytes != null && bytes.length > 0 ? "1".equals(new String(bytes)) : null;
    }
}

package com.example.microserviceone.dtos;

import com.example.microserviceone.domain.Tag;

public record TagDto (String name){
    public static TagDto toDto(Tag tag){
        TagDto tagDto = new TagDto(tag.getName());
        return tagDto;
    }
}

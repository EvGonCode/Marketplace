package com.example.microserviceone.controllers;

import com.example.microserviceone.domain.Tag;
import com.example.microserviceone.dtos.ShopDto;
import com.example.microserviceone.dtos.TagDto;
import com.example.microserviceone.services.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TagController {
    @Autowired
    TagServiceImpl tagService;

    @GetMapping("/tags")
    public List<TagDto> index(){
        return tagService.findAll().stream().map(TagDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/tags/{id}")
    public Tag getTagById(@PathVariable Integer id){
        return tagService.findById(id);
    }

    @PostMapping("/new-tag")
    public String addTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
        return "Tag is saved";
    }
}

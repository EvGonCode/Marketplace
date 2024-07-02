package com.example.microserviceone.services;

import com.example.microserviceone.domain.Tag;
import com.example.microserviceone.dtos.TagDto;
import com.example.microserviceone.repositories.TagRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepo tagRepo;

    @Override
    public List<Tag> findAll() {
        return tagRepo.findAll();
    }

    @Override
    public Tag findById(Integer tagId) {
        return tagRepo.findById(tagId).get();
    }

    @Override
    public void addTag(Tag tag) {
        tagRepo.save(tag);
    }
}

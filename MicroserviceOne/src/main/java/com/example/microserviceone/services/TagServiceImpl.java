package com.example.microserviceone.services;

import com.example.microserviceone.domain.Tag;
import com.example.microserviceone.exception.NoSuchTagException;
import com.example.microserviceone.exception.TagAlreadyExistException;
import com.example.microserviceone.repositories.TagRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepo tagRepo;

    @Override
    public List<Tag> findAll() {
        return tagRepo.findAll();
    }

    @Override
    public Tag findById(Integer tagId) {
        return tagRepo.findById(tagId).get();
    }

    @Override
    public Boolean isExist(String tagName){
        return tagRepo.findByName(tagName).isPresent();
    }

    public void validateUsingTag(String tagName){
        if(!isExist(tagName)){
            throw new NoSuchTagException(tagName);
        }
    }

    public void validateAddingTag(String tagName){
        if(isExist(tagName)){
            throw new TagAlreadyExistException(tagName);
        }
    }

    @Override
    public void addTag(Tag tag) {
        tagRepo.save(tag);
    }
}

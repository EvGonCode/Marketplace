package com.example.microserviceone.services;

import com.example.microserviceone.domain.Tag;
import java.util.List;

public interface TagService {
    List<Tag> findAll();
    Tag findById(Integer tagId);

    Boolean isExist(String tagName);

    void validateUsingTag(String tagName);

    void validateAddingTag(String tagName);
    void addTag(Tag tag);

}

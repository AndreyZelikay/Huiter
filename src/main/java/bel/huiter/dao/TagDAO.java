package bel.huiter.dao;

import bel.huiter.models.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO extends Crud<Tag> {
    List<Tag> getAllTagsStartingWith(String start, Integer skip, Integer top);
    Optional<Tag> findById(long id);
}

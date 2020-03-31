package bel.huiter.DAO;

import bel.huiter.models.Tag;

import java.util.List;

public interface TagDAO extends Crud<Tag> {
    List<Tag> getAllTags();
}

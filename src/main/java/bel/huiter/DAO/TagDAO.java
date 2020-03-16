package bel.huiter.DAO;

import bel.huiter.models.Tag;

import java.util.Optional;

public interface TagDAO extends Crud<Tag> {
    Optional<Tag> findByBody(String body);
}

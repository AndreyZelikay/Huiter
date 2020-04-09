package bel.huiter.dao;

import bel.huiter.models.Photo;

import java.util.Optional;

public interface PhotoDAO extends Crud<Photo> {

    Optional<Photo> findById(long id);
}

package bel.huiter.dao;

import bel.huiter.models.Photo;

import java.util.Optional;

public class PhotoDAOImpl implements PhotoDAO {

    public PhotoDAOImpl() {}

    @Override
    public Optional<Photo> findById(long id) {
        return find(id, Photo.class);
    }
}

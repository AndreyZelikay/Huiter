package bel.huiter.dao;

import bel.huiter.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDAO extends Crud<Comment> {
    List<Comment> findByTwitID(long id);
    Optional<Comment> findById(long id);
}

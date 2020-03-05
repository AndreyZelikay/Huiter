package bel.huiter.DAO;

import bel.huiter.models.Comment;

import java.util.List;

public interface CommentDAO extends Crud<Comment> {
    List<Comment> findByTwitID(long id);
}

package bel.huiter.Services;

import bel.huiter.DAO.CommentDAO;
import bel.huiter.DAO.CommentDAOImpl;
import bel.huiter.models.Comment;

import java.util.List;
import java.util.Optional;

public class CommentService {

    private CommentDAO commentDAO;

    public CommentService() {
        commentDAO = new CommentDAOImpl();
    }

    public void saveToDB(Comment comment) {
        commentDAO.save(comment);
    }

    public void deleteFromDB(Comment comment) {
        commentDAO.delete(comment);
    }

    public void updateInDB(Comment comment) {
        commentDAO.update(comment);
    }

    public Optional<Comment> findById(long id) {
        return commentDAO.find(id);
    }

    public List<Comment> findByTwitID (long id) {
        return commentDAO.findByTwitID(id);
    }
}

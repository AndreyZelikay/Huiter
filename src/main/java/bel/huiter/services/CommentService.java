package bel.huiter.services;

import bel.huiter.dao.CommentDAO;
import bel.huiter.dao.CommentDAOImpl;
import bel.huiter.exceptions.CommentCreateException;
import bel.huiter.exceptions.CommentDeleteException;
import bel.huiter.exceptions.CommentUpdateException;
import bel.huiter.forms.CommentForm;
import bel.huiter.models.Comment;
import bel.huiter.models.Twit;
import bel.huiter.models.User;

import java.util.List;
import java.util.Optional;

public class CommentService {

    private final CommentDAO commentDAO;
    private final UserService userService;
    private final TwitService twitService;

    public CommentService() {
        commentDAO = new CommentDAOImpl();
        userService = new UserService();
        twitService = new TwitService();
    }

    public void createComment(CommentForm form) {
        Optional<User> userOptional = userService.getUserFromJWT(form.getCurrentUserJWT());
        Optional<Twit> twitOptional = twitService.findById(form.getTwitId());

        if(!twitOptional.isPresent()) {
            throw new CommentCreateException("no such twit");
        }

        if(!userOptional.isPresent()) {
            throw new CommentCreateException("invalid user");
        }

        Comment comment = new Comment();
        comment.setBody(form.getBody());
        comment.setOwner(userOptional.get());
        comment.setTwit(twitOptional.get());

        commentDAO.save(comment);
    }

    public void deleteFromDB(Long id, String currentUserJWT) {
        Optional<Comment> commentOptional = commentDAO.findById(id);

        if(!commentOptional.isPresent()) {
            throw new CommentDeleteException("no such comment");
        }

        Optional<User> userOptional = userService.getUserFromJWT(currentUserJWT);

        if(!userOptional.isPresent() || !userOptional.get().equals(commentOptional.get().getOwner())) {
            throw new CommentDeleteException("invalid user");
        }

        commentDAO.delete(commentOptional.get());
    }

    public void updateInDB(CommentForm form) {
        Optional<Comment> commentOptional = commentDAO.findById(form.getCommentId());

        if(!commentOptional.isPresent()) {
            throw new CommentUpdateException("no such comment");
        }

        Optional<User> userOptional = userService.getUserFromJWT(form.getCurrentUserJWT());

        if(!userOptional.isPresent() || !userOptional.get().equals(commentOptional.get().getOwner())) {
            throw new CommentUpdateException("invalid user");
        }

        Comment comment = commentOptional.get();
        comment.setBody(form.getBody());

        commentDAO.update(comment);
    }

    public Optional<Comment> findById(long id) {
        return commentDAO.findById(id);
    }

    public List<Comment> findByTwitID (long id) {
        return commentDAO.findByTwitID(id);
    }
}

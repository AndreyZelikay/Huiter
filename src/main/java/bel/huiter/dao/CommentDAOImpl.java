package bel.huiter.dao;

import bel.huiter.models.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CommentDAOImpl implements CommentDAO {

    private SessionFactory sessionFactory;

    public CommentDAOImpl() {
        sessionFactory = SessionFactoryUtil.getInstance().getSessionFactory();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return find(id, Comment.class);
    }

    @Override
    public List<Comment> findByTwitID(long id) {
        Session session = sessionFactory.openSession();
        Query<Comment> query = session.createQuery("from Comment where twit.id = :twitId",Comment.class);
        query.setParameter("twitId", id);
        List<Comment> result = query.list();
        session.close();
        return result;
    }
}

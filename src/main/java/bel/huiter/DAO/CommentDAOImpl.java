package bel.huiter.DAO;

import bel.huiter.models.Comment;
import bel.huiter.models.Twit;
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
    public void save(Comment object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Comment object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<Comment> find(long id) {
        Session session = sessionFactory.openSession();
        Optional<Comment> comment = Optional.of(session.find(Comment.class,id));
        session.close();
        return comment;
    }

    @Override
    public void delete(Comment object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        session.close();
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

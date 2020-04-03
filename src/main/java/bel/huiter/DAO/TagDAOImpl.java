package bel.huiter.DAO;

import bel.huiter.models.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class TagDAOImpl implements TagDAO {

    private SessionFactory sessionFactory;

    public TagDAOImpl(){
        sessionFactory = SessionFactoryUtil.getInstance().getSessionFactory();
    }

    @Override
    public void save(Tag object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Tag object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<Tag> find(long id) {
        Session session = sessionFactory.openSession();
        Optional<Tag> tag = Optional.of(session.find(Tag.class,id));
        session.close();
        return tag;
    }

    @Override
    public void delete(Tag object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Tag> getAllTags() {
        Session session = sessionFactory.openSession();
        Query<Tag> query = session.createQuery("from Tag as tag", Tag.class);
        List<Tag> result = query.list();
        session.close();
        return result;
    }

    @Override
    public List<Tag> getAllTagsStartingWith(String start) {
        Session session = sessionFactory.openSession();
        Query<Tag> query = session.createQuery("from Tag as tag where tag.body like :start", Tag.class);
        query.setParameter("start", start + "%");
        List<Tag> result = query.list();
        session.close();
        return result;
    }
}

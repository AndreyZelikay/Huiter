package bel.huiter.dao;

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
    public Optional<Tag> findById(long id) {
        return find(id, Tag.class);
    }

    @Override
    public List<Tag> getAllTagsStartingWith(String start, Integer skip, Integer top) {
        Session session = sessionFactory.openSession();
        Query<Tag> query = session.createQuery("from Tag as tag where tag.body like :start ", Tag.class);
        query.setParameter("start", start + "%");
        query.setFirstResult(skip);
        query.setMaxResults(skip + top);
        List<Tag> result = query.list();
        session.close();
        return result;
    }
}

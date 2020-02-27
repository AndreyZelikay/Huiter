package bel.huiter.DAO;

import bel.huiter.models.Tag;
import bel.huiter.models.Twit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TwitDAOImpl implements TwitDAO {

    private SessionFactory sessionFactory;

    public TwitDAOImpl(){
        sessionFactory = SessionFactoryUtil.getInstance().getSessionFactory();
    }

    @Override
    public void save(Twit object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Twit object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<Twit> find(long id) {
        Session session = sessionFactory.openSession();
        Optional<Twit> twit = Optional.of(session.get(Twit.class, id));
        session.close();
        return twit;
    }

    @Override
    public void delete(Twit object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Twit> findTwitsByPeriod(Date from, Date to) {
        Session session = sessionFactory.openSession();
        Query<Twit> query = session.createQuery("from Twit where date >= :from and date <= :to", Twit.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        session.close();
        return query.list();
    }

    @Override
    public List<Twit> findTwitsByTopic(String topic) {
        Session session = sessionFactory.openSession();
        Query<Twit> query = session.createQuery("from Twit where topic = :topic", Twit.class);
        query.setParameter("topic", topic);
        session.close();
        return query.list();
    }

    @Override
    public List<Twit> findTwitsByTags(ArrayList<Tag> tags) {
        Session session = sessionFactory.openSession();
        StringBuilder hqlQuery = new StringBuilder();
        hqlQuery.append("select distinct t from Twit t join t.tags c where c.id =").append(tags.get(0).getId());
        for(Tag tag: tags.subList(1, tags.size())) {
            hqlQuery.append(" or c.id = ").append(tag.getId());
        }
        Query<Twit> query = session.createQuery(hqlQuery.toString(), Twit.class);
        List<Twit> result = query.list();
        session.close();
        result.removeIf(a->a.getTags().size() != tags.size());
        return result;
    }
}

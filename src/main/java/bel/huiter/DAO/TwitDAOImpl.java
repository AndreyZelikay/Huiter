package bel.huiter.DAO;

import bel.huiter.models.Twit;
import bel.huiter.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
    public List<Twit> findTwits(int from, int to, Optional<Date> fromDate, Optional<Date> untilDate, Optional<User> owner, List<String> tags) {
        Session session = sessionFactory.openSession();
        String hqlString = "from Twit twit where ((select count(tag) from Tag tag join tag.twits t where t = twit and tag.body in (:tags)) >= :size)" +
                "and (:from = null or twit.date >= :from) " +
                "and (:until = null or twit.date <= :until) " +
                "and (:owner = null or twit.owner = :owner)";
        Query<Twit> query = session.createQuery(hqlString, Twit.class);
        query.setFirstResult(from);
        query.setMaxResults(to);
        query.setParameter("from", fromDate.orElse(null));
        query.setParameter("until", untilDate.orElse(null));
        query.setParameter("owner", owner.orElse(null));
        query.setParameterList("tags", tags);
        query.setParameter("size",(long) tags.size());
        List<Twit> result = query.list();
        session.close();
        return result;
    }
}

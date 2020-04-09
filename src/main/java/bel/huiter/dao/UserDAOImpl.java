package bel.huiter.dao;

import bel.huiter.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    public UserDAOImpl(){
        sessionFactory = SessionFactoryUtil.getInstance().getSessionFactory();
    }

    @Override
    public Optional<User> findById(long id) {
        return find(id, User.class);
    }

    @Override
    public Optional<User> findByNameAndPassword(String name, String password) {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("from User where name = :name and password = :password", User.class);
        query.setParameter("name", name);
        query.setParameter("password", password);
        Optional<User> user = query.uniqueResultOptional();
        session.close();
        return user;
    }

    @Override
    public Optional<User> findByName(String name) {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("from User where name = :name", User.class);
        query.setParameter("name", name);
        Optional<User> user = query.uniqueResultOptional();
        session.close();
        return user;
    }

}

package bel.huiter.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public interface Crud<T> {

    SessionFactory sessionFactory = SessionFactoryUtil.getInstance().getSessionFactory();

    default void save(T object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        session.close();
    }

    default void update(T object){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        session.close();
    }

    default Optional<T> find(long id, Class<T> typeParameterClass) {
        Session session = sessionFactory.openSession();
        Optional<T> object = Optional.of(session.find(typeParameterClass, id));
        session.close();
        return object;
    }

    default void delete(T object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        session.close();
    }
}

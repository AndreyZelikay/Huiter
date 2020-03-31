package bel.huiter.DAO;

import bel.huiter.models.Photo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class PhotoDAOImpl implements PhotoDAO {

    private SessionFactory sessionFactory;

    public PhotoDAOImpl() {
        sessionFactory = SessionFactoryUtil.getInstance().getSessionFactory();
    }

    @Override
    public void save(Photo photo) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(photo);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Photo photo) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(photo);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<Photo> find(long id) {
        Session session = sessionFactory.openSession();
        Optional<Photo> photo = Optional.of(session.find(Photo.class, id));
        session.close();
        return photo;
    }

    @Override
    public void delete(Photo photo) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(photo);
        session.getTransaction().commit();
        session.close();
    }
}

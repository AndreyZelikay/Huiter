package bel.huiter.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

    private SessionFactory sessionFactory;

    public static SessionFactoryUtil instance;

    private SessionFactoryUtil() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactoryUtil getInstance() {
        if(instance == null){
            instance = new SessionFactoryUtil();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

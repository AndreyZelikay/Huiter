package bel.huiter.DAO;

import bel.huiter.models.Twit;
import bel.huiter.models.User;
import org.hibernate.SessionFactory;

import javax.security.auth.login.Configuration;
import java.util.Optional;

public class TwitDAOImpl implements TwitDAO {

    //private SessionFactory sessionFactory = new Configuration().;



    @Override
    public Optional<User> findByOwner(User owner) {
        return Optional.empty();
    }

    @Override
    public void save(Twit object) {

    }

    @Override
    public void update(Twit object) {

    }

    @Override
    public Optional<Twit> find(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Twit object) {

    }
}

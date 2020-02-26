package bel.huiter.DAO;

import bel.huiter.models.Twit;
import bel.huiter.models.User;

import java.util.Optional;

public interface TwitDAO extends Crud<Twit> {
    Optional<User> findByOwner(User owner);
}

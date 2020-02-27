package bel.huiter.DAO;

import bel.huiter.models.User;

import java.util.Optional;

public interface UserDAO extends Crud<User> {
    Optional<User> findByName(String name);
}

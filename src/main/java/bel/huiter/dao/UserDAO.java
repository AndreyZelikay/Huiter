package bel.huiter.dao;

import bel.huiter.models.User;

import java.util.Optional;

public interface UserDAO extends Crud<User> {
    Optional<User> findByNameAndPassword(String name, String password);
    Optional<User> findByName(String name);
    Optional<User> findById(long id);
}

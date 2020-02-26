package bel.huiter.DAO;

import java.util.Optional;

public interface Crud<T> {
    void save(T object);
    void update(T object);
    Optional<T> find(long id);
    void delete(T object);
}

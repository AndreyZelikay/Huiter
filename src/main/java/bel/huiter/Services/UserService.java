package bel.huiter.Services;

import bel.huiter.DAO.UserDAO;
import bel.huiter.DAO.UserDAOImpl;
import bel.huiter.models.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAOImpl();
    }

    public Optional<User> findById(long id) {
        return userDAO.find(id);
    }

    public boolean validateUser(User user) {
        String password = DigestUtils.md5Hex(user.getPassword());
        return userDAO.findByNameAndPassword(user.getName(), password).isPresent();
    }

    public void saveToDB(User user) {
        userDAO.save(user);
    }

    public Optional<User> findByName(String name) {
        return userDAO.findByName(name);
    }

    public void updateInDB(User user) {
        userDAO.update(user);
    }

    public void deleteFromDB(User user) {
        userDAO.delete(user);
    }
}

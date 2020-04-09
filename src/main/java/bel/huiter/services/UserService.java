package bel.huiter.services;

import bel.huiter.dao.UserDAO;
import bel.huiter.dao.UserDAOImpl;
import bel.huiter.models.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.Optional;

public class UserService {

    private UserDAO userDAO;
    private PhotoService photoService;

    public UserService() {
        try {
            photoService = new PhotoService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userDAO = new UserDAOImpl();
    }

    public Optional<User> findById(long id) {
        return userDAO.findById(id);
    }

    public Optional<User> findUserInDB(User user) {
        String password = DigestUtils.md5Hex(user.getPassword());
        return userDAO.findByNameAndPassword(user.getName(), password);
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
        try {
            photoService.delete(user.getUserPhoto());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userDAO.delete(user);
    }
}

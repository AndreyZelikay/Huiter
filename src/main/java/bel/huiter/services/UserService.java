package bel.huiter.services;

import bel.huiter.dao.UserDAO;
import bel.huiter.dao.UserDAOImpl;
import bel.huiter.exceptions.UserRegistrationException;
import bel.huiter.form_validators.UserRegistrationValidator;
import bel.huiter.forms.UserLoginForm;
import bel.huiter.forms.UserRegistrationForm;
import bel.huiter.jwt.JWT;
import bel.huiter.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;
    private PhotoService photoService;

    public UserService() {
        try {
            photoService = new PhotoService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userDAO = new UserDAOImpl();
    }

    public Optional<User> loginUser(UserLoginForm form) {
        return userDAO.
                findByNameAndPassword(form.getName(), DigestUtils.md5Hex(form.getPassword()));
    }

    public void registerUser(UserRegistrationForm form) throws UserRegistrationException {
        UserRegistrationValidator validator = new UserRegistrationValidator();

        List<String> validationErrors = validator.validate(form);

        if(validationErrors.isEmpty()) {
            try {
                User user = new User();
                user.setName(form.getName());
                user.setPassword(DigestUtils.md5Hex(form.getPassword()));
                user.setUserPhoto(photoService.upload(form.getBase64Photo()));

                userDAO.save(user);
            } catch (IOException e) {
                validationErrors.add("invalid photo");
            } catch (ConstraintViolationException e) {
                validationErrors.add("user already exist!");
            }
        }

        if(!validationErrors.isEmpty()) {
            String errorMessage = String.join("\n", validationErrors);
            throw new UserRegistrationException(errorMessage);
        }
    }

    public Optional<User> getUserFromJWT(String jwt) {
        ObjectMapper objectMapper = new ObjectMapper();

        User user = null;
        try {
            user = objectMapper.readValue(JWT.decodeJWT(jwt).getSubject(), User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(user);
    }

    public void deleteUserFromDB(Long id) {
        try {
            User user = new User();
            user.setId(id);
            photoService.delete(user.getUserPhoto());
            userDAO.delete(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

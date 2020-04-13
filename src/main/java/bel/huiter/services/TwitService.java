package bel.huiter.services;

import bel.huiter.dao.TwitDAO;
import bel.huiter.dao.TwitDAOImpl;
import bel.huiter.exceptions.PhotoUploadException;
import bel.huiter.exceptions.TwitCreationException;
import bel.huiter.exceptions.TwitDeleteException;
import bel.huiter.exceptions.TwitUpdateException;
import bel.huiter.form_validators.TwitValidator;
import bel.huiter.forms.TwitForm;
import bel.huiter.forms.TwitSearchForm;
import bel.huiter.models.Twit;
import bel.huiter.models.User;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TwitService {

    private final TwitDAO twitDAO;
    private PhotoService photoService;
    private final UserService userService;

    public TwitService() {
        try {
            photoService = new PhotoService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userService = new UserService();
        twitDAO = new TwitDAOImpl();
    }

    public void createTwit(TwitForm form) throws TwitCreationException {
        TwitValidator validator = new TwitValidator();

        List<String> validationErrors = validator.validate(form);

        if(validationErrors.isEmpty()) {
            Twit twit = new Twit();
            twit.setBody(form.getBody());
            twit.setDate(new Date());
            twit.setTags(form.getTags());

            Optional<User> userOptional = userService.getUserFromJWT(form.getCurrentUserJWT());

            if(userOptional.isPresent()) {
                twit.setOwner(userOptional.get());
            } else {
                throw new TwitCreationException("invalid user");
            }

            try {
                twit.setPhotos(photoService.uploadAll(form.getBase64Photos()));
            } catch (PhotoUploadException e) {
                throw new TwitCreationException(e.getMessage());
            }

            twitDAO.save(twit);
        } else {
            String errorMessage = String.join("\n", validationErrors);
            throw new TwitCreationException(errorMessage);
        }
    }

    public void deleteFromDB(Long id, String currentUserJWT) {
        Optional<Twit> twitOptional = twitDAO.findById(id);

        if(!twitOptional.isPresent()) {
            throw new TwitDeleteException("no such twit");
        }

        Optional<User> userOptional = userService.getUserFromJWT(currentUserJWT);

        if(!userOptional.isPresent() || !twitOptional.get().getOwner().equals(userOptional.get())) {
            throw new TwitDeleteException("invalid user");
        }

        twitDAO.delete(twitOptional.get());
    }

    public void updateInDB(TwitForm form) throws TwitUpdateException {
        TwitValidator validator = new TwitValidator();

        List<String> validationErrors = validator.validate(form);

        if(validationErrors.isEmpty()) {
            Optional<Twit> twitOptional = twitDAO.findById(form.getId());

            if(!twitOptional.isPresent()) {
                throw new TwitUpdateException("no such twit");
            }

            Twit twit = twitOptional.get();

            Optional<User> userOptional = userService.getUserFromJWT(form.getCurrentUserJWT());

            if(!userOptional.isPresent() || !userOptional.get().equals(twit.getOwner())) {
                throw new TwitUpdateException("invalid user");
            }

            twit.setBody(form.getBody());
            twit.setTags(form.getTags());

            try {
                twit.setPhotos(photoService.uploadAll(form.getBase64Photos()));
            } catch (PhotoUploadException e) {
                throw new TwitUpdateException(e.getMessage());
            }

            twitDAO.update(twit);
        } else {
            String errorMessage = String.join("\n", validationErrors);
            throw new TwitUpdateException(errorMessage);
        }
    }

    public Optional<Twit> findById(long id) {
        return twitDAO.findById(id);
    }

    public List<Twit> findTwits(TwitSearchForm twitSearchForm) {
        return twitDAO.findTwits(twitSearchForm);
    }
}

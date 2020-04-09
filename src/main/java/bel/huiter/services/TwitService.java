package bel.huiter.services;

import bel.huiter.dao.TwitDAO;
import bel.huiter.dao.TwitDAOImpl;
import bel.huiter.models.Twit;
import bel.huiter.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TwitService {

    private TwitDAO twitDAO;

    public TwitService() {
        twitDAO = new TwitDAOImpl();
    }

    public void saveToDB(Twit twit) {
        twit.setDate(new Date());
        twitDAO.save(twit);
    }

    public void deleteFromDB(Twit twit) {
        twitDAO.delete(twit);
    }

    public void updateInDB(Twit twit) {
        twitDAO.update(twit);
    }

    public Optional<Twit> findById(long id) {
        return twitDAO.findById(id);
    }

    public List<Twit> findTwits(int from, int to, Optional<Date> fromDate, Optional<Date> untilDate, Optional<User> owner, List<String> tags) {
        return twitDAO.findTwits(from, to, fromDate, untilDate, owner, tags);
    }
}

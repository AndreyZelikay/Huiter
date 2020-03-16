package bel.huiter.Services;

import bel.huiter.DAO.TwitDAO;
import bel.huiter.DAO.TwitDAOImpl;
import bel.huiter.models.Tag;
import bel.huiter.models.Twit;
import bel.huiter.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TwitService {

    private TwitDAO twitDAO;

    public TwitService() {
        twitDAO = new TwitDAOImpl();
    }

    public void saveToDB(Twit twit) {
        twitDAO.save(twit);
    }

    public void deleteFromDB(Twit twit) {
        twitDAO.delete(twit);
    }

    public void updateInDB(Twit twit) {
        twitDAO.update(twit);
    }

    public Optional<Twit> findById(long id) {
        return twitDAO.find(id);
    }

    public List<Twit> findTwits(int from, int to, Optional<Date> fromDate, Optional<Date> untilDate, Optional<User> owner, List<Tag> tags) {
        return twitDAO.findTwits(from, to, fromDate, untilDate, owner, tags);
    }
}

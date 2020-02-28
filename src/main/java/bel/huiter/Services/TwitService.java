package bel.huiter.Services;

import bel.huiter.DAO.TwitDAO;
import bel.huiter.DAO.TwitDAOImpl;
import bel.huiter.models.Tag;
import bel.huiter.models.Twit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TwitService {

    private TwitDAO twitDAO;

    public TwitService() {
        twitDAO = new TwitDAOImpl();
    }

    public void save(Twit twit) {
        twitDAO.save(twit);
    }

    public void update(Twit twit) {
        twitDAO.update(twit);
    }

    public Optional<Twit> findById(long id) {
        return twitDAO.find(id);
    }

    public List<Twit> findByPeriodTime(Date from, Date until) {
        return twitDAO.findByPeriod(from, until);
    }

    public List<Twit> findByTopic(String topic) {
        return twitDAO.findByTopic(topic);
    }

    public List<Twit> findByTags(ArrayList<Tag> tags) {
        return twitDAO.findByTags(tags);
    }

}

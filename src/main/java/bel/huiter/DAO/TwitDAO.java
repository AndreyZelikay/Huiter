package bel.huiter.DAO;

import bel.huiter.models.Tag;
import bel.huiter.models.Twit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface TwitDAO extends Crud<Twit> {
    List<Twit> findByPeriod(Date from, Date until);

    List<Twit> findByTopic(String topic);

    List<Twit> getTwitsInInterval(int form, int to);

    List<Twit> findByTags(ArrayList<Tag> tags);
}

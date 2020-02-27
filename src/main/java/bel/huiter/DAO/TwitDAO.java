package bel.huiter.DAO;

import bel.huiter.models.Tag;
import bel.huiter.models.Twit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface TwitDAO extends Crud<Twit> {
    List<Twit> findTwitsByPeriod(Date from, Date to);

    List<Twit> findTwitsByTopic(String topic);

    List<Twit> findTwitsByTags(ArrayList<Tag> tags);
}

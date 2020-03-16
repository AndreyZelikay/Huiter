package bel.huiter.DAO;

import bel.huiter.models.Tag;
import bel.huiter.models.Twit;
import bel.huiter.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TwitDAO extends Crud<Twit> {
    List<Twit> findTwits(int from, int to, Optional<Date> fromDate, Optional<Date> untilDate, Optional<User> owner, List<Tag> tags);
}

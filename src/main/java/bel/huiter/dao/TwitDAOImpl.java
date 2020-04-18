package bel.huiter.dao;

import bel.huiter.forms.TwitSearchForm;
import bel.huiter.models.Tag;
import bel.huiter.models.Twit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TwitDAOImpl implements TwitDAO {

    private final SessionFactory sessionFactory;

    public TwitDAOImpl(){
        sessionFactory = SessionFactoryUtil.getInstance().getSessionFactory();
    }

    @Override
    public Optional<Twit> findById(long id) {
        return find(id, Twit.class);
    }

    @Override
    public void save(Twit twit) {
        prepareTwitTagsToSave(twit);
        TwitDAO.super.save(twit);
    }

    @Override
    public void update(Twit twit) {
        prepareTwitTagsToSave(twit);
        TwitDAO.super.update(twit);
    }

    @Override
    public void delete(Twit twit) {
        List<Tag> tags = getTwitTagsInDB(twit);
        twit.setTags(tags.stream().filter(this::isTagUsingOnce).collect(Collectors.toList()));

        TwitDAO.super.delete(twit);
    }

    @Override
    public List<Twit> findTwits(TwitSearchForm form) {
        Session session = sessionFactory.openSession();
        String hqlString = "from Twit twit where " +
                "((select count(tag) from Tag tag join tag.twits t where t = twit and tag.body in (:tags)) >= :size)" +
                "and (:from = null or twit.date >= :from) " +
                "and (:until = null or twit.date <= :until) " +
                "and (:owner = null or twit.owner.name = :owner)";

        Query<Twit> query = session.createQuery(hqlString, Twit.class);
        query.setFirstResult(form.getSkip());
        query.setMaxResults(form.getSkip() + form.getTop());
        query.setParameter("from", form.getFromDate());
        query.setParameter("until", form.getUntilDate());
        query.setParameter("owner", form.getOwnerName());
        query.setParameterList("tags", form.getTags());
        query.setParameter("size",(long) form.getTags().size());

        List<Twit> result = query.list();
        session.close();

        return result;
    }

    private void prepareTwitTagsToSave(Twit twit) {
        if(twit.getTags() == null) {
            return;
        }

        twit.getTags().sort(Comparator.comparing(Tag::getBody));
        List<Tag> tagsFromDB = getTwitTagsInDB(twit);

        for(int i = 0; i < twit.getTags().size(); ) {
            Tag currentTag = twit.getTags().get(i);
            int indexOfCurrentTagInDB = tagsFromDB.indexOf(currentTag);

            if (indexOfCurrentTagInDB != -1) {
                currentTag.setId(tagsFromDB.get(indexOfCurrentTagInDB).getId());
            }

            while (i < twit.getTags().size() && twit.getTags().get(i).equals(currentTag)) {
                twit.getTags().set(i, currentTag);
                i++;
            }
        }
    }

    private List<Tag> getTwitTagsInDB(Twit twit) {
        Session session = sessionFactory.openSession();
        Query<Tag> query;
        List<Tag> result;

        if(twit.getId() == 0) {
            query = session.createQuery("from Tag as tag where tag.body in (:tags)", Tag.class);
            query.setParameter("tags", twit.getTags().stream().map(Tag::getBody).collect(Collectors.toList()));
            result = query.list();
        } else {
            result = new ArrayList<>(session.find(Twit.class, twit.getId()).getTags());
        }
        session.close();

        return result;
    }

    private boolean isTagUsingOnce(Tag tag) {
        Session session = sessionFactory.openSession();
        Query<Long> query = session.createQuery("select count(twit) from Twit as twit join twit.tags tag where tag.id = :id", Long.class);
        query.setParameter("id", tag.getId());
        Long result = query.getSingleResult();
        session.close();

        return result <= 1;
    }
}

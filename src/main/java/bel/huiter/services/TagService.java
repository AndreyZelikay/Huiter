package bel.huiter.services;

import bel.huiter.dao.TagDAO;
import bel.huiter.dao.TagDAOImpl;
import bel.huiter.models.Tag;

import java.util.List;

public class TagService {

    private final TagDAO tagDAO;

    public TagService() {
        tagDAO = new TagDAOImpl();
    }

    public List<Tag> getAllTagsStartingWith(String start, Integer skip, Integer top) {
        return tagDAO.getAllTagsStartingWith(start, skip, top);
    }
}

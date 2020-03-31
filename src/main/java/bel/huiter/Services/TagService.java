package bel.huiter.Services;

import bel.huiter.DAO.TagDAO;
import bel.huiter.DAO.TagDAOImpl;
import bel.huiter.models.Tag;

import java.util.List;

public class TagService {

    private TagDAO tagDAO;

    public TagService() {
        tagDAO = new TagDAOImpl();
    }

    public List<Tag> getAllTags() {
        return tagDAO.getAllTags();
    }

    public void saveToDB(Tag tag) {
        tagDAO.save(tag);
    }
}

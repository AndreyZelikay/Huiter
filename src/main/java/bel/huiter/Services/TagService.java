package bel.huiter.Services;

import bel.huiter.DAO.TagDAO;
import bel.huiter.DAO.TagDAOImpl;
import bel.huiter.models.Tag;

import java.util.Optional;

public class TagService {

    private TagDAO tagDAO;

    public TagService() {
        tagDAO = new TagDAOImpl();
    }

    public Optional<Tag> findByBody(String body) {
        return tagDAO.findByBody(body);
    }
}

package bel.huiter;

import bel.huiter.DAO.TagDAO;
import bel.huiter.DAO.TagDAOImpl;
import bel.huiter.DAO.TwitDAO;
import bel.huiter.DAO.TwitDAOImpl;

public class Main {
    public static void main(String[] args) {
        TwitDAO twitDAO = new TwitDAOImpl();
        TagDAO tagDAO = new TagDAOImpl();
    }
}

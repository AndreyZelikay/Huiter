package bel.huiter.servlets.TwitServlets;

import bel.huiter.services.TwitService;
import bel.huiter.models.Twit;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostLikeServlet extends HttpServlet {

    private TwitService twitService;

    @Override
    public void init() throws ServletException {
        twitService = new TwitService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        ObjectMapper objectMapper = new ObjectMapper();

        Twit twit = objectMapper.readValue(json, Twit.class);

        if(req.getParameter("option").equals("like")) {
            twit.addLIke();
        } else if(req.getParameter("option").equals("dislike")) {
            twit.addDislike();
        }

        twitService.updateInDB(twit);
    }
}

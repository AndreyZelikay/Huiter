package bel.huiter.Servlets.TwitServlets;

import bel.huiter.Services.TwitService;
import bel.huiter.models.Twit;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class GetCommentsServlet extends HttpServlet {

    TwitService twitService;

    @Override
    public void init() throws ServletException {
        twitService = new TwitService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Integer.parseInt(req.getParameter("id"));
        Optional<Twit> twit = twitService.findById(id);
        if(twit.isPresent()) {
            resp.getWriter().write(new ObjectMapper().writeValueAsString(twit.get().getComments()));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

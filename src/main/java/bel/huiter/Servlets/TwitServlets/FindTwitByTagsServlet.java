package bel.huiter.Servlets.TwitServlets;

import bel.huiter.Services.TwitService;
import bel.huiter.models.Tag;
import bel.huiter.models.Twit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/twit/findByTags")
public class FindTwitByTagsServlet extends HttpServlet {

    private TwitService twitService;

    @Override
    public void init() throws ServletException {
        twitService = new TwitService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonString = req.getParameter("tags");
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Tag> tags = objectMapper.readValue(jsonString,new TypeReference<ArrayList<Tag>>(){});
        List<Twit> result = twitService.findByTags(tags);
        resp.getWriter().write(objectMapper.writeValueAsString(result));
    }
}

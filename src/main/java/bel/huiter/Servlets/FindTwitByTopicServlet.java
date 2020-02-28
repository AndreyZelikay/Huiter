package bel.huiter.Servlets;

import bel.huiter.Services.TwitService;
import bel.huiter.models.Twit;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/twit/findByTopic")
public class FindTwitByTopicServlet extends HttpServlet {

    private TwitService twitService;

    @Override
    public void init() throws ServletException {
        twitService = new TwitService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topic = req.getParameter("topic");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Twit> result = twitService.findByTopic(topic);
        resp.getWriter().write(objectMapper.writeValueAsString(result));
    }
}

package bel.huiter.Servlets.TwitServlets;

import bel.huiter.Json.JsonView;
import bel.huiter.Services.TwitService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/twit/getTwitsInInterval")
public class GetTwitsInIntervalServlet extends HttpServlet {

    private TwitService twitService;

    @Override
    public void init() throws ServletException {
        twitService = new TwitService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int from = Integer.parseInt(req.getParameter("from"));
        int to =  Integer.parseInt(req.getParameter("to"));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithView(JsonView.Twit.class)
                .writeValueAsString(twitService.getTwitsInInterval(from, to));
        resp.getWriter().write(json);
    }
}

package bel.huiter.servlets.twit_servlets;

import bel.huiter.forms.TwitSearchForm;
import bel.huiter.json.JsonView;
import bel.huiter.models.Twit;
import bel.huiter.services.TwitService;
import bel.huiter.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/twit/getTwits")
public class SearchTwitsServlet extends HttpServlet {

    private TwitService twitService;
    private UserService userService;

    @Override
    public void init() {
        twitService = new TwitService();
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        ObjectMapper objectMapper = new ObjectMapper();

        TwitSearchForm form = objectMapper.readValue(json, TwitSearchForm.class);

        List<Twit> resultList = twitService.findTwits(form);

        resp.getWriter().write(objectMapper.writerWithView(JsonView.Twit.class)
                .writeValueAsString(resultList));
    }
}

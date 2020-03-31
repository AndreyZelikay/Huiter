package bel.huiter.Servlets.TwitServlets;

import bel.huiter.Json.JsonView;
import bel.huiter.Services.TwitService;
import bel.huiter.Services.UserService;
import bel.huiter.models.Twit;
import bel.huiter.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@WebServlet("/twit/getTwits")
public class GetTwitsServlet extends HttpServlet {

    private TwitService twitService;
    private UserService userService;

    @Override
    public void init() {
        twitService = new TwitService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int from = Integer.parseInt(req.getParameter("from"));
        int to = Integer.parseInt(req.getParameter("to"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        Optional<Date> fromDate = Optional.empty();
        Optional<Date> untilDate = Optional.empty();
        try {
            System.out.println(req.getParameter("fromDate"));
            fromDate = Optional.of(dateFormat.parse(req.getParameter("fromDate")));
            untilDate = Optional.of(dateFormat.parse(req.getParameter("untilDate")));
        } catch (ParseException | NullPointerException ignored) {}

        Optional<User> owner = userService.findByName(req.getParameter("ownerName"));

        String tagsString = req.getParameter("tags");
        List<String> tags = new ArrayList<>();
        if(!tagsString.isEmpty()) {
            tags = Arrays.asList(req.getParameter("tags").split(" "));
        }

        List<Twit> result = twitService.findTwits(from, to, fromDate, untilDate, owner, tags);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithView(JsonView.Twit.class)
                .writeValueAsString(result);
        resp.getWriter().write(json);
    }
}

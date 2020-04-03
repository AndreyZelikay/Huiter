package bel.huiter.Servlets.TwitServlets;

import bel.huiter.JWT.JWT;
import bel.huiter.Services.TwitService;
import bel.huiter.Services.UserService;
import bel.huiter.models.Twit;
import bel.huiter.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/twit/create")
public class CreateTwitServlet extends HttpServlet {

    private TwitService twitService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        twitService = new TwitService();
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        ObjectMapper objectMapper = new ObjectMapper();

        String jwt = req.getHeader("token");
        User user = objectMapper.readValue(JWT.decodeJWT(jwt).getSubject(), User.class);
        Optional<User> userOptional = userService.findById(user.getId());

        if(userOptional.isPresent()) {
            Twit twit = objectMapper.readValue(jsonString, Twit.class);
            twit.setOwner(userOptional.get());
            twitService.saveToDB(twit);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

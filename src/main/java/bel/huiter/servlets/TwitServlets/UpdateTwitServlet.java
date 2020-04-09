package bel.huiter.servlets.TwitServlets;

import bel.huiter.jwt.JWT;
import bel.huiter.models.Twit;
import bel.huiter.models.User;
import bel.huiter.services.TwitService;
import bel.huiter.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/twit/update")
public class UpdateTwitServlet extends HttpServlet {

    private TwitService twitService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        twitService = new TwitService();
        userService = new UserService();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        ObjectMapper objectMapper = new ObjectMapper();

        Twit twitToUpdate = objectMapper.readValue(jsonString, Twit.class);
        Optional<Twit> twitOptional = twitService.findById(twitToUpdate.getId());

        String jwt = req.getHeader("token");
        User user = objectMapper.readValue(JWT.decodeJWT(jwt).getSubject(), User.class);
        Optional<User> userOptional = userService.findById(user.getId());

        if(!twitOptional.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if(!userOptional.isPresent() || !twitOptional.get().getOwner().equals(userOptional.get())) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            twitToUpdate.setOwner(userOptional.get());
            twitService.updateInDB(twitToUpdate);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }


}

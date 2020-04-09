package bel.huiter.servlets.TwitServlets;

import bel.huiter.jwt.JWT;
import bel.huiter.services.TwitService;
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

@WebServlet("/twit/delete")
public class DeleteTwitServlet extends HttpServlet {

    private TwitService twitService;

    @Override
    public void init() throws ServletException {
        twitService = new TwitService();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String jwt = req.getHeader("token");

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(JWT.decodeJWT(jwt).getSubject(), User.class);

        Optional<Twit> twitOptional = twitService.findById(id);

        if(!twitOptional.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if(twitOptional.get().getOwner().getId() != user.getId()){
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            twitService.deleteFromDB(twitOptional.get());
        }
    }
}

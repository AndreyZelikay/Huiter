package bel.huiter.servlets.twit_servlets;

import bel.huiter.exceptions.TwitCreationException;
import bel.huiter.forms.TwitForm;
import bel.huiter.services.TwitService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/twit/create")
public class CreateTwitServlet extends HttpServlet {

    private TwitService twitService;

    @Override
    public void init() throws ServletException {
        twitService = new TwitService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        String jwt = req.getHeader("token");

        ObjectMapper objectMapper = new ObjectMapper();

        TwitForm form = objectMapper.readValue(jsonString, TwitForm.class);
        form.setCurrentUserJWT(jwt);

        try {
            twitService.createTwit(form);
        } catch (TwitCreationException e) {
            resp.getWriter().write(e.getMessage());
        }
    }
}

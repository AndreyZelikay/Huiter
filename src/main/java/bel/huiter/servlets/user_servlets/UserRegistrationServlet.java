package bel.huiter.servlets.user_servlets;

import bel.huiter.exceptions.UserRegistrationException;
import bel.huiter.forms.UserRegistrationForm;
import bel.huiter.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/registration")
public class UserRegistrationServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            UserRegistrationForm form = objectMapper.readValue(json, UserRegistrationForm.class);
            userService.registerUser(form);
        } catch (UserRegistrationException e) {
            resp.getWriter().write(e.getMessage());
        }
    }
}

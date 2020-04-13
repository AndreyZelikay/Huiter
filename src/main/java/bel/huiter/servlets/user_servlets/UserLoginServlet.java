package bel.huiter.servlets.user_servlets;

import bel.huiter.forms.UserLoginForm;
import bel.huiter.json.JsonView;
import bel.huiter.jwt.JWT;
import bel.huiter.models.User;
import bel.huiter.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        ObjectMapper objectMapper = new ObjectMapper();

        UserLoginForm form = objectMapper.readValue(json, UserLoginForm.class);

        Optional<User> authorizedUser = userService.loginUser(form);

        if(authorizedUser.isPresent()) {
            resp.setHeader("token", JWT.createJTW(objectMapper
                    .writerWithView(JsonView.JWT.class)
                    .writeValueAsString(authorizedUser.get())));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
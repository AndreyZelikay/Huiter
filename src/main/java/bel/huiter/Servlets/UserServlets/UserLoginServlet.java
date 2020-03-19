package bel.huiter.Servlets.UserServlets;

import bel.huiter.JWT.JWT;
import bel.huiter.Json.JsonView;
import bel.huiter.Services.UserService;
import bel.huiter.models.User;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(json, User.class);
        Optional<User> userOptional = userService.findUserInDB(user);
        if(userOptional.isPresent()) {
            String jwtBody = new ObjectMapper().writerWithView(JsonView.JWT.class).writeValueAsString(userOptional.get());
            resp.setHeader("token", JWT.createJTW(jwtBody));
            resp.getWriter().write(objectMapper.writerWithView(JsonView.User.class).writeValueAsString(userOptional.get()));
        } else {
            resp.getWriter().write("failure");
        }
    }
}

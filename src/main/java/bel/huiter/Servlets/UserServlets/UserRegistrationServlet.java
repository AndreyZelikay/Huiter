package bel.huiter.Servlets.UserServlets;

import bel.huiter.JWT.JWT;
import bel.huiter.Json.JsonView;
import bel.huiter.Services.UserService;
import bel.huiter.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/registration")
public class UserRegistrationServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(jsonString, User.class);
        user.setStatus("USER");
        try {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            userService.saveToDB(user);
            String jwtBody = new ObjectMapper().writerWithView(JsonView.JWT.class).writeValueAsString(user);
            resp.setHeader("token", JWT.createJTW(jwtBody));
        } catch (ConstraintViolationException e) {
            resp.getWriter().write("user already exist!");
        }
    }
}

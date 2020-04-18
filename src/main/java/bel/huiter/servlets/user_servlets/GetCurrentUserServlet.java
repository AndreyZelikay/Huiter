package bel.huiter.servlets.user_servlets;

import bel.huiter.json.JsonView;
import bel.huiter.models.User;
import bel.huiter.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/user/getUser")
public class GetCurrentUserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String jwt = req.getHeader("token");

        Optional<User> userOptional = userService.getUserFromJWT(jwt);

        if(userOptional.isPresent()) {
            Optional<User> userFromDB = userService.findById(userOptional.get().getId());
            if(userFromDB.isPresent()) {
                resp.getWriter().write(new ObjectMapper()
                        .writerWithView(JsonView.User.class)
                        .writeValueAsString(userFromDB.get()));
            } else {
                resp.getWriter().write("no such user");
            }
        } else {
            resp.getWriter().write("invalid jwt");
        }
    }
}

package bel.huiter.servlets.user_servlets;

import bel.huiter.json.JsonView;
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

@WebServlet("/user/getUser")
public class GetCurrentUserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jwt = req.getHeader("token");

        Optional<User> userOptional = userService.getUserFromJWT(jwt);

        if(userOptional.isPresent()) {
            resp.getWriter().write(new ObjectMapper()
                    .writerWithView(JsonView.User.class)
                    .writeValueAsString(userOptional.get()));
        } else {
            resp.getWriter().write("invalid jwt");
        }
    }
}

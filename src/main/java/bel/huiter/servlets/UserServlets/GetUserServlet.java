package bel.huiter.servlets.UserServlets;

import bel.huiter.jwt.JWT;
import bel.huiter.json.JsonView;
import bel.huiter.services.UserService;
import bel.huiter.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/user/getUser")
public class GetUserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jwt = req.getHeader("token");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(JWT.decodeJWT(jwt).getSubject(), User.class);
        Optional<User> userOptional = userService.findById(user.getId());
        System.out.println(userOptional.get().getTwits());
        if(userOptional.isPresent()) {
            resp.getWriter().write(objectMapper.writerWithView(JsonView.User.class)
                    .writeValueAsString(userOptional.get()));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

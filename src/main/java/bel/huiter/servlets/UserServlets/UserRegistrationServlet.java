package bel.huiter.servlets.UserServlets;

import bel.huiter.jwt.JWT;
import bel.huiter.json.JsonView;
import bel.huiter.services.PhotoService;
import bel.huiter.services.UserService;
import bel.huiter.models.Photo;
import bel.huiter.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/registration")
public class UserRegistrationServlet extends HttpServlet {

    private UserService userService;
    private PhotoService photoService;

    @Override
    public void init() {
        userService = new UserService();
        try {
            photoService = new PhotoService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> valuesMap = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        String base64Icon = (String) valuesMap.get("icon");
        User user = mapper.convertValue(valuesMap.get("user"), User.class);

        user.setStatus("USER");
        try {
            Photo photo = photoService.upload(base64Icon);

            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            user.setUserPhoto(photo);
            userService.saveToDB(user);

            String jwtBody = new ObjectMapper().writerWithView(JsonView.JWT.class).writeValueAsString(user);
            resp.setHeader("token", JWT.createJTW(jwtBody));
        } catch (ConstraintViolationException e) {
            resp.getWriter().write("user already exist!");
        }
    }
}

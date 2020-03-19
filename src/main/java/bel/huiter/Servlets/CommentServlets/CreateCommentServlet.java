package bel.huiter.Servlets.CommentServlets;

import bel.huiter.JWT.JWT;
import bel.huiter.Json.JsonView;
import bel.huiter.Services.CommentService;
import bel.huiter.models.Comment;
import bel.huiter.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/comment/create")
public class CreateCommentServlet extends HttpServlet {

    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        commentService = new CommentService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        String jwt = req.getHeader("token");

        ObjectMapper objectMapper = new ObjectMapper();

        Comment comment = objectMapper.readValue(jsonString, Comment.class);
        User user = objectMapper.readValue(JWT.decodeJWT(jwt).getSubject(), User.class);
        comment.setOwner(user);
        commentService.saveToDB(comment);

        String jsonOutput = objectMapper.writerWithView(JsonView.Comment.class)
                .writeValueAsString(commentService.findByTwitID(comment.getTwit().getId()));
        resp.getWriter().write(jsonOutput);
    }
}

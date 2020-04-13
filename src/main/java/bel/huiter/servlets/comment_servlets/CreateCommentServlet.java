package bel.huiter.servlets.comment_servlets;

import bel.huiter.exceptions.CommentCreateException;
import bel.huiter.forms.CommentForm;
import bel.huiter.services.CommentService;
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

        CommentForm form = new ObjectMapper().readValue(jsonString, CommentForm.class);
        form.setCurrentUserJWT(jwt);

        try{
            commentService.createComment(form);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (CommentCreateException e) {
            resp.getWriter().write(e.getMessage());
        }
    }
}

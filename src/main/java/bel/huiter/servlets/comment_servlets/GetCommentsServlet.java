package bel.huiter.servlets.comment_servlets;

import bel.huiter.json.JsonView;
import bel.huiter.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/comment/getComments")
public class GetCommentsServlet extends HttpServlet {

    private CommentService commentService;

    @Override
    public void init(){
        commentService = new CommentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Integer.parseInt(req.getParameter("id"));

            resp.getWriter().write(new ObjectMapper()
                    .writerWithView(JsonView.Comment.class)
                    .writeValueAsString(commentService.findByTwitID(id)));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

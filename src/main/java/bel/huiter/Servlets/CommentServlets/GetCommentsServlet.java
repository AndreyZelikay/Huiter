package bel.huiter.Servlets.CommentServlets;

import bel.huiter.Json.JsonView;
import bel.huiter.Services.CommentService;
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
        long id = Integer.parseInt(req.getParameter("id"));
        ObjectMapper objectMapper = new ObjectMapper();
        String json  = objectMapper.writerWithView(JsonView.Comment.class).writeValueAsString(commentService.findByTwitID(id));
        resp.getWriter().write(json);
    }
}

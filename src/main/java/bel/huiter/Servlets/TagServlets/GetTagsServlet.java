package bel.huiter.Servlets.TagServlets;

import bel.huiter.Services.TagService;
import bel.huiter.models.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tag/getTags")
public class GetTagsServlet extends HttpServlet {

    private TagService tagService;

    @Override
    public void init() throws ServletException {
        tagService = new TagService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String startingWith = req.getParameter("startingWith");
        List<Tag> tags;
        if(startingWith == null || startingWith.isEmpty()){
            tags = tagService.getAllTags();
        } else {
            tags = tagService.getAllTagsStartingWith(startingWith);
        }
        resp.getWriter().write(new ObjectMapper().writeValueAsString(tags));
    }
}

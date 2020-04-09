package bel.huiter.servlets.TagServlets;

import bel.huiter.models.Tag;
import bel.huiter.services.TagService;
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
        int skip;
        try {
            skip = Integer.parseInt(req.getParameter("skip"));
        } catch (NumberFormatException e) {
            skip = 0;
        }
        int top;
        try {
            top = Integer.parseInt(req.getParameter("top"));
        } catch (NumberFormatException e) {
            top = 5;
        }

        List<Tag> tags = tagService.getAllTagsStartingWith(startingWith, skip, top);

        resp.getWriter().write(new ObjectMapper().writeValueAsString(tags));
    }
}

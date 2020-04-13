package bel.huiter.servlets.twit_servlets;

import bel.huiter.exceptions.TwitDeleteException;
import bel.huiter.services.TwitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/twit/delete")
public class DeleteTwitServlet extends HttpServlet {

    private TwitService twitService;

    @Override
    public void init() throws ServletException {
        twitService = new TwitService();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            String jwt = req.getHeader("token");

            twitService.deleteFromDB(id, jwt);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (TwitDeleteException e) {
            resp.getWriter().write(e.getMessage());
        }
    }
}

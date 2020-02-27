package bel.huiter.Servlets;

import bel.huiter.DAO.TwitDAOImpl;
import bel.huiter.models.Twit;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/twit")
public class TwitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();

        Gson gson = new Gson();

        List<Twit> twits = new TwitDAOImpl().findTwitsByTopic("topic");

        out.print(gson.toJson(twits));
    }
}

package bel.huiter.Servlets.TwitServlets;

import bel.huiter.Services.TwitService;
import bel.huiter.models.Twit;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/twit/findByDate")
public class FindByDateServlet extends HttpServlet {

    private TwitService twitService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        try {
            Date until = dateFormat.parse(req.getParameter("until"));
            Date from = dateFormat.parse(req.getParameter("from"));
            List<Twit> result = twitService.findByPeriodTime(from, until);
            resp.getWriter().write(new ObjectMapper().writeValueAsString(result));
        } catch (ParseException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } catch (NullPointerException e){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

package bel.huiter.Filters;

import bel.huiter.JWT.JWT;
import bel.huiter.JWT.SecurityConstants;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter("/twit/*")
public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        try {
            String jwt = httpServletRequest.getHeader("token");
            Claims claims = JWT.decodeJWT(jwt);
            if (claims != null && claims.getIssuer().equals(SecurityConstants.ISSUER)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                servletResponse.getWriter().write("invalid token");
            }
        }
        catch(Exception e) {
            servletResponse.getWriter().write(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}

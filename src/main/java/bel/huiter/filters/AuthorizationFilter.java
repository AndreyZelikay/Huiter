package bel.huiter.filters;

import bel.huiter.jwt.JWT;
import bel.huiter.jwt.SecurityConstants;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

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
            long currentTimeInMillis = System.currentTimeMillis();
            if (claims == null || !claims.getIssuer().equals(SecurityConstants.ISSUER)) {
                servletResponse.getWriter().write("invalid token");
            } else if(claims.getExpiration().after(new Date(currentTimeInMillis))) {
                servletResponse.getWriter().write("token expired");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
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

package filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jwt.JwtManager;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "JwtFilter",
        urlPatterns = {"/*"})
public class JwtFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        CustomLogger.logEnter(this.getClass(),"doFilter",req.toString(),chain.toString());

        String tokenHeader = ((HttpServletRequest)req).getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring( new String("Bearer").length());
            Jws<Claims> claims = JwtManager.getInstance().parseToken(token);
            if ( claims != null ) {
                String subject = claims.getBody().getSubject();
                String role = (String) claims.getBody().get("loggedInAs");
                req.setAttribute("loggedInAs", role);
            }
        }
        chain.doFilter(req, resp);

        CustomLogger.logExit(this.getClass(),"doFilter",resp.toString());
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

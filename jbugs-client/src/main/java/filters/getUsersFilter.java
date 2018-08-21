package filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jwt.JwtManager;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;

import javax.servlet.Filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "getUsersFilter",
        urlPatterns = "/rest/users")
public class getUsersFilter implements Filter {
    private UserManagement userManagement;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        System.out.println("+++++++++++++++Inside filter one.+++++++++++++++++");
        String tokenHeader = ((HttpServletRequest)req).getHeader("token");
        System.out.println(tokenHeader);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

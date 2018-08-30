package filters;

import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

//This filter check if the user have rights to perform any actions which comes to the following address
//http://localhost:8080/jbugs/rest/bugs
@WebFilter(filterName = "BugsFilter",
        urlPatterns = "/rest/bugs/*")
public class BugsFilter implements Filter {
    @EJB
    private UserManagement userManagement;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {


        HttpServletRequest httReq = (HttpServletRequest) req;
        String reqHead = httReq.getHeader("Access-Control-Allow-Origin");
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;

        //The filter checks if the request is OPTION, in this case the filter ignore the request
        if (((HttpServletRequest) req).getMethod().equalsIgnoreCase("OPTIONS")) {
            chain.doFilter(req, resp);
            CustomLogger.logExit(this.getClass(), "doFilter", resp.toString());
            return;
        }
        String currentUser = ((HttpServletRequest) req).getHeader("currentUser");
        String webToken = ((HttpServletRequest) req).getHeader("webtoken");
        String userPermision = "BUG_MANAGEMENT";
        Set<String> permissions;
        //Check if the user is logged in
        if (!userManagement.checkLoggedUser(currentUser, webToken)) {
            if (null == httpServletResponse.getHeader("Access-Control-Allow-Origin")) {
                httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
            }
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            CustomLogger.logExit(this.getClass(), "doFilter", HttpServletResponse.SC_FORBIDDEN + "");
            return;
        }
        permissions = userManagement.getAllUserPermission(currentUser);
        //Check if the user do not have rights to perform the action
        if (!permissions.contains(userPermision)) {
            if (null == httpServletResponse.getHeader("Access-Control-Allow-Origin")) {
                httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
            }
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            CustomLogger.logExit(this.getClass(), "doFilter", HttpServletResponse.SC_UNAUTHORIZED + "");
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}

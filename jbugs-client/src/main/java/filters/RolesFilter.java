package filters;

import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebFilter(filterName = "RolesFilter",
        urlPatterns = "/rest/roles")
public class RolesFilter implements Filter {
    @EJB
    private UserManagement userManagement;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("+++++++++++++++Inside filter one.+++++++++++++++++");

        HttpServletRequest httReq = (HttpServletRequest) req;
        String reqHead = httReq.getHeader("Access-Control-Allow-Origin");
        HttpServletResponse httpServletResponse = (HttpServletResponse)resp;

        if(((HttpServletRequest) req).getMethod().equalsIgnoreCase("OPTIONS")){
            System.out.println("Received Options");
            chain.doFilter(req, resp);
            return;
        }
        String currentUser = ((HttpServletRequest)req).getHeader("currentUser");
        String webToken = ((HttpServletRequest)req).getHeader("webtoken");
        System.out.println("+++++++++++++CURENT USER++++++++++++"+currentUser);
        System.out.println("+++++++++++++CURENT TOKEN++++++++++++"+webToken);
        String userPermision="PERMISSION_MANAGEMENT";
        Set<String> permissions;
        if(!userManagement.checkLoggedUser(currentUser,webToken)){
            if(null == httpServletResponse.getHeader("Access-Control-Allow-Origin")){
                httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
            }
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN );
            return;
        }
        System.out.println("USER ALLOWED");
        permissions=userManagement.getAllUserPermission(currentUser);
        if(!permissions.contains(userPermision)){
            if(null == httpServletResponse.getHeader("Access-Control-Allow-Origin")){
                httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
            }
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        System.out.println("USER HAVE RIGHTS AND IS LOGGED IN");
        //String tokenHeader = ((HttpServletRequest)req).getHeader("token");
        //System.out.println(tokenHeader);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

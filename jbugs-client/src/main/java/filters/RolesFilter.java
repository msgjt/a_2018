package filters;

import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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
        System.out.println("+++++++++++++++Inside filter roles.+++++++++++++++++");
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
            throw new IOException("USER NOT LOGGED IN");
        }
        System.out.println("USER ALLOWED");
        permissions=userManagement.getAllUserPermission(currentUser);
        if(!permissions.contains(userPermision)){
            throw new IOException("USER DO NOT HAVE RIGHTS");
        }
        System.out.println("USER HAVE RIGHTS TO SEE ROLES");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

package resources;


import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/isbugclose")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class IsBugCloseOnServer {

    @EJB
    UserManagement userManagement;

    @POST
    public boolean checkLoggedIn(String body) {
        String[] userToken = body.split("   ");
        Set<String> permissions;
        permissions = userManagement.getAllUserPermission(userToken[0]);
        if (permissions.contains("BUG_CLOSE")){
            return true;
        }else return false;
    }
}

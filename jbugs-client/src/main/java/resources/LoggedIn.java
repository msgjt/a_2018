package resources;

import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Path("/loggedin")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class LoggedIn {

    @EJB
    UserManagement userManagement;

    @POST
    public boolean checkLoggedIn(String body){
        String[]userToken=body.split("   ");
        boolean isLoggedIn= userManagement.checkLoggedUser(userToken[0],userToken[1]);
        return isLoggedIn;
    }
}

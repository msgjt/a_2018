package resources;

import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/logout")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class Logout {

    @EJB
    private UserManagement userManagement;

    @POST
    public Response logoutUser(String username){
        System.out.println("ESTE USRENAME ----"+username);
        Response resp = Response.status(Response.Status.OK)
                .entity(userManagement.logout(username))
                .build();
            return resp;
    }

}

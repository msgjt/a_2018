package resources;


import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @EJB
    private UserManagement userManagement;

    @GET
    public List<UserDTO> getUsers(){
        return userManagement.getAllUsers();
    }

    @POST
    public Response createUser(UserDTO userDTO){
        try {
            return Response.status(Response.Status.CREATED)
                    .entity(userManagement.createUser(userDTO))
                    .build();
        }
        catch (BusinessException be){
            throw new RuntimeException("User could not be created");
        }
    }

}

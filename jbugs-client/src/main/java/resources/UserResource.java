package resources;

import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.utils.CustomLogger;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @EJB
    private UserManagement userManagement;

    @GET
    public List<UserDTO> getUsers() {
        return userManagement.getAllUsers();
    }

    @POST
    public Response createUser(UserDTO userDTO) throws BusinessException {
        CustomLogger.logEnter(this.getClass(),"createUser",userDTO.toString());

        Response response = Response.status(Response.Status.CREATED)
                .entity(userManagement.createUser(userDTO))
                .build();
        CustomLogger.logExit(this.getClass(),"createUser",response.toString());
        return response;
    }

    @Path("/{id}")
    @GET
    public UserDTO getUserById(@PathParam("id") Long id) {

        return userManagement.getUserById(id);
    }


    @PUT
    public Response updateUser(UserDTO userDTO) throws BusinessException {
        return Response.status(Response.Status.OK)
                .entity(userManagement.updateUser(userDTO))
                .build();
    }

    @Path("/deactivate")
    @PUT
    public Response deactivateUser(UserDTO userDTO) throws BusinessException {
        return Response.status(Response.Status.OK)
                .entity(userManagement.deactivateUser(userDTO.getId()))
                .build();
    }

    @Path("/activate")
    @PUT
    public Response activateUser(UserDTO userDTO) throws BusinessException {
        return Response.status(Response.Status.OK)
                .entity(userManagement.activateUser(userDTO.getId()))
                .build();
    }

}




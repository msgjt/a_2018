package resources;

import ro.msg.edu.jbugs.userManagement.business.control.NotifiManagement;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @EJB
    private UserManagement userManagement;

    @EJB
    private NotifiManagement notifiManagement;

    @GET
    public List<UserDTO> getUsers() {
        return userManagement.getAllUsers();
    }

    @POST
    public Response createUser(UserDTO userDTO) throws BusinessException {
        CustomLogger.logEnter(this.getClass(), "createUser", userDTO.toString());

        Response response = Response.status(Response.Status.CREATED)
                .entity(userManagement.createUser(userDTO))
                .build();
        CustomLogger.logExit(this.getClass(), "createUser", response.toString());
        return response;
    }

    @Path("/{id}")
    @GET
    public UserDTO getUserById(@PathParam("id") Long id) {

        return userManagement.getUserById(id);
    }

    @Path("/changePassword")
    @PUT
    public UserDTO updateUserPassword(UserDTO userDTO) {

        return userManagement.updateUserPassword(userDTO.getId(), userDTO.getPassword());
    }

    @PUT
    public Response updateUser(UserDTO userDTO, @QueryParam("id") Long id) throws BusinessException {

        UserDTO oldUser = userManagement.getUserById(userDTO.getId());
        UserDTO newUser = userManagement.updateUser(userDTO);
        String message = Arrays.toString(new String[]{oldUser.toString(), newUser.toString()});

        Response result = Response.status(Response.Status.OK)
                .entity(newUser)
                .build();
        notifiManagement.sendNotification("USER_UPDATED",message,"",userDTO.getId(),id);
        return result;
    }

    @Path("/deactivate")
    @PUT
    public Response deactivateUser(UserDTO userDTO) throws BusinessException {
        UserDTO deactivated = userManagement.deactivateUser(userDTO.getId());

        Response result = Response.status(Response.Status.OK)
                .entity(deactivated)
                .build();


        List<Long> ids = userManagement.getAllUsers().stream()
                .filter(u -> userManagement.getAllUserPermissionAsList(u.getUsername()).stream()
                        .anyMatch(p -> p.getType2().equals("USER_MANAGEMENT")))
                .map(UserDTO::getId)
                .collect(Collectors.toList());

        notifiManagement.sendNotification("USER_DELETED",deactivated.toString(),"",ids.toArray(new Long[]{}));
        return result;
    }


    @Path("/activate")
    @PUT
    public Response activateUser(UserDTO userDTO) throws BusinessException {
        return Response.status(Response.Status.OK)
                .entity(userManagement.activateUser(userDTO.getId()))
                .build();
    }

}




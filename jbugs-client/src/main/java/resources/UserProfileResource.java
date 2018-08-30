package resources;

import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.control.NotificationManagement;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/userprofile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserProfileResource {

    @EJB
    private UserManagement userManagement;

    @EJB
    private NotificationManagement notificationManagement;

    @GET
    public List<UserDTO> getUsers() {
        return userManagement.getAllUsers();
    }

    @PUT
    public Response updateUser(UserDTO userDTO, @QueryParam("id") Long id) throws BusinessException {

        UserDTO oldUser = userManagement.getUserById(userDTO.getId());
        UserDTO newUser = userManagement.updateUser(userDTO);
        String message = Arrays.toString(new String[]{oldUser.toString(), newUser.toString()});

        Response result = Response.status(Response.Status.OK)
                .entity(newUser)
                .build();
        notificationManagement.sendNotification("USER_UPDATED", message, "", userDTO.getId(), id);
        return result;
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
}

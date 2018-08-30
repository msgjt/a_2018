package resources;

import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.PermissionDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.PermissionDTOHelper;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/userpermissions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserPermissions {
    @EJB
    private UserManagement userManagement;

    @Path("/{username}")
    @GET
    public List<PermissionDTO> getUsersPermissions(@PathParam("username") String username) {
        return userManagement.getAllUserPermissionAsList(username).stream()
                .map(PermissionDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }
}

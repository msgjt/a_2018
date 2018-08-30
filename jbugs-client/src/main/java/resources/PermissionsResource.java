package resources;

import ro.msg.edu.jbugs.userManagement.business.control.PermissionManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.PermissionDTO;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/permissions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PermissionsResource {
    @EJB
    private PermissionManagement permissionManagement;

    @GET
    public List<PermissionDTO> getPermissions() {

        return permissionManagement.getAllPermissions();

    }
}

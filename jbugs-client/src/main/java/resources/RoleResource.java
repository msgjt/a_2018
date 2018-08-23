package resources;


import ro.msg.edu.jbugs.userManagement.business.control.RoleManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/roles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleResource {

    @EJB
    private RoleManagement roleManagement;

    /**
     * GET REST method. Will return a list of all the roleDTOs. Should be guarded from unauthorized accesses.
     *
     * @return
     */
    @GET
    public List<RoleDTO> getRoles() { return roleManagement.getAllRoles(); }

    /**
     *
     * @param roleDTO
     * @return
     */
    @POST
    public Response updateRole(RoleDTO roleDTO) {
        try{
            return Response.status(Response.Status.OK)
                    .entity(roleManagement.updateRole(roleDTO))
                    .build();
        }
        catch (BusinessException e){
            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }
    }

}

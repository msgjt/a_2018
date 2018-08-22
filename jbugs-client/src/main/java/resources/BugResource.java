package resources;

import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/bugs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BugResource {


    @EJB
    private BugManagement bugManagement;


    @GET
    public List<BugDTO> getUsers(){
        return bugManagement.getAllBugs();
    }

    @POST
    public Response createBug(BugDTO bugDTO){
        try {
            return Response.status(Response.Status.CREATED)
                    .entity(bugManagement.createBug(bugDTO))
                    .build();
        }
        catch (BusinessException be){
            throw new RuntimeException("Bug could not be created");
        }

    }

    @Path("/{id}")
    @GET
    public BugDTO getBugById(@PathParam("id") Long id){

        return bugManagement.getBugById(id);
    }



}

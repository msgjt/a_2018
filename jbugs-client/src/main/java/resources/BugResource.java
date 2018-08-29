package resources;

import org.glassfish.jersey.internal.inject.Custom;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;

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
    public List<BugDTO> getBugs(){
        CustomLogger.logEnter(this.getClass(),"getBugs","");

        List<BugDTO> bugs = bugManagement.getAllBugs();

        CustomLogger.logExit(this.getClass(),"getBugs",bugs.toString());
        return bugs;
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

    @PUT
    public Response updateBug(BugDTO bugDTO) {
        CustomLogger.logEnter(this.getClass(),"updateBug",bugDTO.toString());

        BugDTO result = bugManagement.updateBug(bugDTO);

        CustomLogger.logExit(this.getClass(),"updateBug",result.toString());
        return Response.status(Response.Status.OK)
                .entity(result)
                .build();
    }

    @Path("/{id}")
    @GET
    public BugDTO getBugById(@PathParam("id") Long id){

        return bugManagement.getBugById(id);
    }



}

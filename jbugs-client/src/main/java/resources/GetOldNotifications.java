package resources;

import ro.msg.edu.jbugs.userManagement.business.control.NotificationManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/getoldnotifications")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class GetOldNotifications {

    @EJB
    NotificationManagement notificationManagement;

    @Path("/{id}")
    @GET
    public List<NotificationDTO> getOldNotifications(@PathParam("id") Long id){
        return notificationManagement.getOldNotifications(id);}
}

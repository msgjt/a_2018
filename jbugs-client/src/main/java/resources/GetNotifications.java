package resources;


import ro.msg.edu.jbugs.userManagement.business.control.NotificationManagement;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/getnotifications")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class GetNotifications {

    @EJB
    NotificationManagement notificationManagement;

    @Path("/{id}")
    @GET
    public List<NotificationDTO> getNewNotifications(@PathParam("id") Long id){
        System.out.println("--------------------"+id+"--");
        return notificationManagement.getNewNotifications(id);
    }

}
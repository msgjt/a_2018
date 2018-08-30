package resources;

import ro.msg.edu.jbugs.userManagement.business.control.NotificationManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/oldnotifications")
public class OldNotificationResource {

    @EJB
    private NotificationManagement notificationManagement;

    @GET
    public List<NotificationDTO> getUnreadNotificationsByUserId(@QueryParam("id") Long id) {

        return notificationManagement.getReadNotificationsForUser(id);

    }
}

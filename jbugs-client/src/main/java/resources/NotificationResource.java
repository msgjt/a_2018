package resources;

import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.business.control.NotifiManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/notifications")
public class NotificationResource {

    @EJB
    private NotifiManagement notifiManagement;

    @GET
    public List<NotificationDTO> getUnreadNotificationsByUserId(@QueryParam("id") Long id) {
        CustomLogger.logEnter(this.getClass(),"getUnreadNotificationsByUserId",id + "");

        List<NotificationDTO> notifications = notifiManagement.getUnreadNotificationsForUser(id);

        CustomLogger.logExit(this.getClass(),"getUnreadNotificationsByUserId",notifications.toString());
        return notifications;
    }
}

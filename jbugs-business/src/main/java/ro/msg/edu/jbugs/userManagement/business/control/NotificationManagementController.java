package ro.msg.edu.jbugs.userManagement.business.control;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTOHelper;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.utils.CustomLogger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationManagementController implements NotificationManagement {

    @EJB
    private UserPersistenceManager userPersistenceManager;

    @Override
    public List<NotificationDTO> getAllNotifications() {
        CustomLogger.logEnter(this.getClass(),"getAllNotifications","");

        List<NotificationDTO> result = userPersistenceManager.getAllNotifications().stream()
                .map(NotificationDTOHelper::fromEntity)
                .collect(Collectors.toList());

        CustomLogger.logExit(this.getClass(),"getAllNotifications",result.toString());
        return result;
    }
}

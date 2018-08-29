package ro.msg.edu.jbugs.userManagement.business.control;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTOHelper;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationManagementController implements NotificationManagement {

    @EJB
    private UserPersistenceManager userPersistenceManager;

    @EJB
    private UserManagement userManagementController;

    @Override
    public List<NotificationDTO> getAllNotifications() {
        CustomLogger.logEnter(this.getClass(),"getAllNotifications","");

        List<NotificationDTO> result = userPersistenceManager.getAllNotifications().stream()
                .map(NotificationDTOHelper::fromEntity)
                .collect(Collectors.toList());

        CustomLogger.logExit(this.getClass(),"getAllNotifications",result.toString());
        return result;
    }

    public List<NotificationDTO> getNewNotifications(Long id){
        Thread t1= new Thread();
        t1.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        List<Notification> newNotifications= new ArrayList<>();
        List<NotificationDTO> newNotificationsDTO = new ArrayList<>();

        do {
            newNotifications = userPersistenceManager.getAllNotificationsForUser(id);
            newNotificationsDTO = newNotifications.stream()
                    .map(NotificationDTOHelper::fromEntity)
                    .collect(Collectors.toList());

            newNotifications.stream()
                    .forEach(notification -> {
                        notification.setStatus("read");
                        userPersistenceManager.updateNotification(notification);
                    });

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(newNotifications.size() ==0);

        return newNotificationsDTO;
    }


    public List<NotificationDTO> getOldNotifications(Long id){
        List<Notification> newNotifications= new ArrayList<>();
        List<NotificationDTO> oldNotificationsDTO = new ArrayList<>();

            newNotifications = userPersistenceManager.getOldNotificationsForUser(id);
            oldNotificationsDTO = newNotifications.stream()
                    .map(NotificationDTOHelper::fromEntity)
                    .collect(Collectors.toList());

        return oldNotificationsDTO;
    }
}

package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import java.util.List;

public interface NotifiManagement {

    List<NotificationDTO> getUnreadNotificationsForUser(Long id);
    NotificationDTO createNotification(NotificationDTO notificationDTO);
    NotificationDTO updateNotification(NotificationDTO notificationDTO);

    Notification internalCreateNotification(Notification notification);
    Notification internalUpdateNotification(Notification notification);

    Notification getNotificationById(Long id);

    Notification assignUserToNotification(Long notificationId, Long userId);

    void sendNotification(String notificationType, String notificationMessage,
                          String notificationURL, Long... userIds);


}

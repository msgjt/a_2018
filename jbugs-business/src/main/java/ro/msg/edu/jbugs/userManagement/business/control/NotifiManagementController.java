package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.DetailedExceptionCode;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTOHelper;
import ro.msg.edu.jbugs.userManagement.persistence.dao.NotificationPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;
import ro.msg.edu.jbugs.userManagement.persistence.entity.UsersNotifications;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotifiManagementController implements NotifiManagement {

    @EJB
    private NotificationPersistenceManager notificationPersistenceManager;

    @EJB
    private UserPersistenceManager userPersistenceManager;

    @Override
    public List<NotificationDTO> getUnreadNotificationsForUser(Long id) {


        List<Notification> notifications = notificationPersistenceManager.getNotificationsForUser(id);

        return notifications.stream()
                .filter( n -> n.getStatus().equals("not_read"))
                .map(n -> {
                    n.setStatus("read");
                    notificationPersistenceManager.update(n);
                    return NotificationDTOHelper.fromEntity(n);
                })
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {

        return NotificationDTOHelper.fromEntity(
                notificationPersistenceManager.add(NotificationDTOHelper.toEntity(notificationDTO))
        );

    }

    @Override
    public NotificationDTO updateNotification(NotificationDTO notificationDTO) {

        return NotificationDTOHelper.fromEntity(
                notificationPersistenceManager.update(NotificationDTOHelper.toEntity(notificationDTO))
        );
    }

    @Override
    public Notification internalCreateNotification(Notification notification) {

        return notificationPersistenceManager.add(notification);
    }

    @Override
    public Notification internalUpdateNotification(Notification notification) {

        return notificationPersistenceManager.update(notification);

    }

    @Override
    public Notification getNotificationById(Long id) {

        return notificationPersistenceManager.getById(id)
                .orElseThrow(() -> new BusinessException(
                        ExceptionCode.NOTIFICATION_VALIDATION_EXCEPTION,
                        DetailedExceptionCode.NOTIFICATION_NOT_FOUND)
                );

    }

    @Override
    public Notification assignUserToNotification(Long notificationId, Long userId) {

        Notification notification = notificationPersistenceManager.getNotificationWithUsers(notificationId)
                .orElseThrow(() -> new BusinessException(
                        ExceptionCode.NOTIFICATION_VALIDATION_EXCEPTION,
                        DetailedExceptionCode.NOTIFICATION_NOT_FOUND)
                );

        User user = userPersistenceManager.getUserById(userId)
                .orElseThrow(() -> new BusinessException(
                        ExceptionCode.USER_VALIDATION_EXCEPTION,
                        DetailedExceptionCode.USER_NOT_FOUND)
                );

        notification.addUser(user);

        return notificationPersistenceManager.update(notification);
    }

    public void sendNotification(String notificationType,String notificationMessage,
                                 String notificationURL, Long... userIds) {

        Arrays.stream(userIds).forEach(userId -> {
            User user = userPersistenceManager.getUserById(userId)
                    .orElseThrow(() -> new BusinessException(
                            ExceptionCode.USER_VALIDATION_EXCEPTION,
                            DetailedExceptionCode.USER_NOT_FOUND
                    ));
            Notification notification = new Notification();
            notification.setStatus("not_read");
            notification.setMessage(notificationMessage);
            notification.setType(notificationType);
            notification.setURL(notificationURL);

            Notification added = notificationPersistenceManager.add(notification);
            user.addNotification(added);
            userPersistenceManager.updateUser(user);
        });
    }


}

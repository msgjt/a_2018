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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationManagementController implements NotificationManagement {




    @EJB
    private NotificationPersistenceManager notificationPersistenceManager;

    @EJB
    private UserPersistenceManager userPersistenceManager;




    /**
     * Forms a list of all the notifications with status "non_read" for a given user while
     * changing each non_read status to "read".
     *
     * @param id not null, the id of the user for which to check the notifications
     * @return the list of all unread notifications
     */
    @Override
    public List<NotificationDTO> getUnreadNotificationsForUser(@NotNull Long id) {


        List<Notification> notifications = notificationPersistenceManager.getNotificationsForUser(id);

        return notifications.stream()
                .filter(n -> n.getStatus().equals("not_read"))
                .map(n -> {
                    n.setStatus("read");
                    notificationPersistenceManager.update(n);
                    return NotificationDTOHelper.fromEntity(n);
                })
                .collect(Collectors.toList());
    }

    /**
     * Creates a notification from a notificationDTO by calling the add method from the persistence layer,
     * and the NotificationDTOHelper methods.
     *
     * @param notificationDTO not null, the dto to be persisted
     * @return the persisted dto
     */
    @Override
    public NotificationDTO createNotification(@NotNull NotificationDTO notificationDTO) {

        return NotificationDTOHelper.fromEntity(
                notificationPersistenceManager.add(NotificationDTOHelper.toEntity(notificationDTO))
        );

    }

    /**
     * Returns a notification for a given id. Should not be used outside the business layer.
     *
     * @param id not null, the search key
     * @return the detached entity from the persistence layer
     */
    @Override
    public Notification getNotificationById(@NotNull Long id) {

        return notificationPersistenceManager.getById(id)
                .orElseThrow(() -> new BusinessException(
                        ExceptionCode.NOTIFICATION_VALIDATION_EXCEPTION,
                        DetailedExceptionCode.NOTIFICATION_NOT_FOUND)
                );

    }

    /**
     * Assigns a user to a notification, thus adding a new join entity (UsersNotifications)
     *
     * @param notificationId not null, the id of the notification
     * @param userId not null, the id of the user
     * @return the persisted notification entity
     */
    @Override
    public Notification assignUserToNotification(@NotNull Long notificationId,@NotNull Long userId) {

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

    /**
     * Creates a new notification and add its to the user described by the userIds params.
     *
     * @param notificationType not null, the type of the notification
     * @param notificationMessage not null, the message of the notification
     * @param notificationURL not null, the url of the notification
     * @param userIds not null, variable number of params, each describing a user id
     */
    @SuppressWarnings("all")
    public void sendNotification(@NotNull String notificationType,@NotNull  String notificationMessage,
                                 @NotNull  String notificationURL,@NotNull  Long... userIds) {

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

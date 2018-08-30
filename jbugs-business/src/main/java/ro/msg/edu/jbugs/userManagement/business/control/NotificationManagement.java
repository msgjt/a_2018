package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;

import java.util.List;

public interface NotificationManagement {


    /**
     * Forms a list of all the notifications with status "non_read" for a given user while
     * changing each non_read status to "read".
     *
     * @param id not null, the id of the user for which to check the notifications
     * @return the list of all unread notifications
     */
    List<NotificationDTO> getUnreadNotificationsForUser(Long id);


    /**
     * Creates a notification from a notificationDTO by calling the add method from the persistence layer,
     * and the NotificationDTOHelper methods.
     *
     * @param notificationDTO not null, the dto to be persisted
     * @return the persisted dto
     */
    NotificationDTO createNotification(NotificationDTO notificationDTO);


    /**
     * Returns a notification for a given id. Should not be used outside the business layer.
     *
     * @param id not null, the search key
     * @return the detached entity from the persistence layer
     */
    Notification getNotificationById(Long id);


    /**
     * Assigns a user to a notification, thus adding a new join entity (UsersNotifications)
     *
     * @param notificationId not null, the id of the notification
     * @param userId not null, the id of the user
     * @return the persisted notification entity
     */
    Notification assignUserToNotification(Long notificationId, Long userId);


    /**
     * Creates a new notification and add its to the user described by the userIds params.
     *
     * @param notificationType not null, the type of the notification
     * @param notificationMessage not null, the message of the notification
     * @param notificationURL not null, the url of the notification
     * @param userIds not null, variable number of params, each describing a user id
     */
    void sendNotification(String notificationType, String notificationMessage,
                          String notificationURL, Long... userIds);


}

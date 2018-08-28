package ro.msg.edu.jbugs.userManagement.business.dto;

import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;

public class NotificationDTOHelper {


    public static NotificationDTO fromEntity(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setType(notification.getType());
        notificationDTO.setURL(notification.getURL());
        notificationDTO.setStatus(notification.getStatus());
        return notificationDTO;
    }

    public static Notification toEntity(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setId(notificationDTO.getId());
        notification.setMessage(notificationDTO.getMessage());
        notification.setType(notificationDTO.getType());
        notification.setURL(notificationDTO.getURL());
        notification.setStatus(notificationDTO.getStatus());
        return notification;
    }

}

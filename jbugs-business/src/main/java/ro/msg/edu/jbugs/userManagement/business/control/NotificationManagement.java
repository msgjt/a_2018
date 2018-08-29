package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.PermissionDTO;

import java.util.List;

public interface NotificationManagement {
    List<NotificationDTO> getAllNotifications();
    List<NotificationDTO> getNewNotifications(Long id);
    List<NotificationDTO> getOldNotifications(Long id);
}

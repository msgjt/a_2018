package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;

import java.util.List;

public interface NotifiManagement {

    List<NotificationDTO> getUnreadNotificationsForUser(Long id);

}

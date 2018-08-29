package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTOHelper;
import ro.msg.edu.jbugs.userManagement.persistence.dao.NotificationPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotifiManagementController implements NotifiManagement{

    @EJB
    private NotificationPersistenceManager notificationPersistenceManager;

    @Override
    public List<NotificationDTO> getUnreadNotificationsForUser(Long id) {
        List<Notification> notifications =  notificationPersistenceManager.getUnreadNotificationsForUser(id)
                .stream()
                .filter(n -> n.getStatus().equals("not_read"))
                .collect(Collectors.toList());
        if( notifications.size() == 0)
            return new ArrayList<>();

        List<NotificationDTO> result = notifications
                                        .stream()
                                        .map(NotificationDTOHelper::fromEntity)
                                        .collect(Collectors.toList());
        notifications.forEach( n -> {
            n.setStatus("read");
            notificationPersistenceManager.update(n);
        });

        return result;
    }
}

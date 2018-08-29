package ro.msg.edu.jbugs.userManagement.persistence.dao;

import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationPersistenceManager {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;

    public List<Notification> getUnreadNotificationsForUser(@NotNull Long id) {
        CustomLogger.logEnter(this.getClass(),"getUnreadNotificationsForUser",id + "");


        TypedQuery<User> tq =em.createNamedQuery(User.GET_NOTIFICATIONS,User.class)
                .setParameter("id",id);
        User user = tq.getSingleResult();

        List<Notification> result = user.getNotifications().stream()
                .map(n -> n.getNotification())
                .collect(Collectors.toList());


        CustomLogger.logExit(this.getClass(),"getUnreadNotificationsForUser",result.toString());
        return result;
    }

    public Notification update(Notification notification){
        System.out.println("STATUS: " + notification.getStatus());
        Notification persisted = em.find(Notification.class,notification.getId());
        persisted.setStatus(notification.getStatus());

        Notification result = em.merge(persisted);

        return result;
    }
}

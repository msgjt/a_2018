package ro.msg.edu.jbugs.userManagement.persistence.dao;

import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;
import ro.msg.edu.jbugs.userManagement.persistence.entity.UsersNotifications;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class NotificationPersistenceManager {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;

    public List<Notification> getNotificationsForUser(@NotNull Long id) {
        CustomLogger.logEnter(this.getClass(),"getNotificationsForUser",id + "");


        TypedQuery<Notification> tq =em.createNamedQuery(User.GET_NOTIFICATIONS_FOR_USER,Notification.class)
                .setParameter("id",id);
        List<Notification> result = tq.getResultList();


        CustomLogger.logExit(this.getClass(),"getNotificationsForUser",result.toString());
        return result;

    }

    public Optional<Notification> getNotificationWithUsers(@NotNull Long notificationId) {

        TypedQuery<Notification> typedQuery = em.createNamedQuery(Notification.GET_WITH_USERS,Notification.class)
                .setParameter("id",notificationId);
        Notification notification = typedQuery.getSingleResult();

        return Optional.ofNullable(notification);

    }

    public Notification update(@NotNull Notification notification){

        Notification persisted = em.find(Notification.class,notification.getId());
        persisted.copyFieldsFrom(notification);
        return em.merge(persisted);

    }

    public Notification add(@NotNull Notification notification){

        em.persist(notification);
        em.flush();
        return notification;

    }

    public void remove(@NotNull Notification notification){

        em.remove(notification);

    }

    public Optional<Notification> getById(@NotNull Long id){

        return Optional.ofNullable(em.find(Notification.class,id));

    }

    public Optional<Notification> getByType(@NotNull String type){

        TypedQuery<Notification> typedQuery = em.createNamedQuery(Notification.GET_BY_TYPE,Notification.class)
                .setParameter("type",type);

        Notification notification = typedQuery.getSingleResult();

        return Optional.ofNullable(notification);

    }




}

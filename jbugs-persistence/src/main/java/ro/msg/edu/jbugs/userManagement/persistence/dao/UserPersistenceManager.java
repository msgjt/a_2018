package ro.msg.edu.jbugs.userManagement.persistence.dao;

import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Provides functions for working with users in the persistence layer.
 */
@Stateless
public class UserPersistenceManager {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;


    /**
     * Persists a user in the database.
     *
     * @param user : user entity to be created, should not be null
     * @return : inserted user entity from database
     */
    public User createUser(@NotNull User user) {
        CustomLogger.logEnter(this.getClass(), "createUser", user.toString());
        em.persist(user);
        em.flush();

        CustomLogger.logExit(this.getClass(), "createUser", user.toString());
        return user;
    }

    /**
     * Updates a user from the database.
     *
     * @param user : user entity to be updated, should not be null
     * @return : updated user entity from database
     */
    public User updateUser(@NotNull User user) {
        CustomLogger.logEnter(this.getClass(), "updateUser", user.toString());

        User old = em.find(User.class, user.getId());
        old.copyFieldsFrom(user);
        em.persist(old);

        CustomLogger.logExit(this.getClass(), "updateUser", old.toString());
        return old;
    }

    /**
     * Get a list of all users from the database.
     *
     * @return : ResultList, empty if there are no users in the database.
     */
    public List<User> getAllUsers() {
        CustomLogger.logEnter(this.getClass(), "getAllUsers", "");

        List<User> result = em.createNamedQuery(User.GET_ALL_USERS, User.class)
                .getResultList();

        CustomLogger.logExit(this.getClass(), "getAllUsers", result.toString());
        return result;
    }


    /**
     * Returns a user entity with the matching username wrapped in an optional.
     * If none exist, returns an empty Optional Object
     *
     * @param username : String containing the username.
     * @return : Optional, containing a user entity.
     */
    public Optional<User> getUserByUsername(@NotNull String username) {
        CustomLogger.logEnter(this.getClass(), "getUserByUsername", username);

        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_USERNAME, User.class)
                .setParameter("username", username);
        Optional<User> result;

        try {
            result = Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            result = Optional.empty();
        }

        CustomLogger.logExit(this.getClass(), "getUserByUsername", result.toString());
        return result;

    }


    public Optional<User> getUserById(@NotNull Long id) {
        CustomLogger.logEnter(this.getClass(), "getUserById", String.valueOf(id));

        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_ID, User.class)
                .setParameter("id", id);

        Optional<User> result;
        try {
            result = Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            result = Optional.empty();
        }

        CustomLogger.logEnter(this.getClass(), "getUserById", result.toString());
        return result;
    }


    /**
     * Persists a user in the database.
     *
     * @param role : role entity to be created, should not be null
     */
    public Role createRole(@NotNull Role role) {
        CustomLogger.logEnter(this.getClass(), "createRole", role.toString());

        em.persist(role);
        em.flush();

        CustomLogger.logExit(this.getClass(), "createRole", role.toString());
        return role;
    }

    /**
     * Persists a notification in the database.
     *
     * @param notification
     * @return
     */
    public Notification createNotification(@NotNull Notification notification) {
        CustomLogger.logEnter(this.getClass(), "createNotification", notification.toString());

        em.persist(notification);
        em.flush();

        CustomLogger.logExit(this.getClass(), "createNotification", notification.toString());
        return notification;
    }

    /**
     * Removes a role from the database.
     *
     * @param role : role entity to be removed, should not be null
     */
    public void removeRole(Role role) {
        CustomLogger.logEnter(this.getClass(), "removeRole", role.toString());

        em.remove(role);
        em.flush();

        CustomLogger.logExit(this.getClass(), "removeRole", role.toString());
    }

    /**
     * Removes a notification from the database.
     *
     * @param notification : notification entity to be removed, should not be null
     */
    public void removeNotification(Notification notification) {
        CustomLogger.logEnter(this.getClass(), "removeNotification", notification.toString());

        em.remove(notification);
        em.flush();

        CustomLogger.logExit(this.getClass(), "removeNotification", notification.toString());
    }

    /**
     * Updates a role in the database using the given Role entity.
     *
     * @param role : role entity to be updated, should not be null
     * @return : returns the updated role entity
     */
    public Role updateRole(Role role) {
        CustomLogger.logEnter(this.getClass(), "updateRole", role.toString());

        Role result = em.merge(role);

        CustomLogger.logExit(this.getClass(), "updateRole", result.toString());
        return result;
    }

    /**
     * Updates a notification in the database using the given Notification entity.
     *
     * @param notification : notification entity to be updated, should not be null
     * @return : returns the updated notification entity
     */
    public Notification updateNotification(Notification notification) {
        CustomLogger.logEnter(this.getClass(), "updateRole", notification.toString());

        Notification result = em.merge(notification);
        em.flush();

        CustomLogger.logExit(this.getClass(), "updateRole", result.toString());
        return result;
    }

    /**
     * TODO: nu cred ca avem nevoie de metoda asta - nu am mai facut-o frumoasa
     * Returns the role with the given id
     *
     * @param id : id
     * @return : Role entity
     */
    public Role getRoleForId(long id) {
        CustomLogger.logEnter(this.getClass(), "getRoleForId", String.valueOf(id));

        Query q = em.createQuery("SELECT r FROM Role r WHERE r.id=" + id);
        Role result = (Role) q.getSingleResult();

        CustomLogger.logExit(this.getClass(), "getRoleForId", result.toString());
        return result;
    }

    public Notification getNotificationForId(long id) {
        CustomLogger.logEnter(this.getClass(), "getNotificationForId", String.valueOf(id));

        Query q = em.createQuery("SELECT n FROM Notification n WHERE n.id=" + id);
        Notification result = (Notification) q.getSingleResult();

        CustomLogger.logExit(this.getClass(), "getNotificationForId", result.toString());
        return result;
    }

    /**
     * Get a list of all roles stored in the database.
     *
     * @return : List of Roles, empty if there are no roles in the database.
     */
    public List<Role> getAllRoles() {
        CustomLogger.logEnter(this.getClass(), "getAllRoles", "");

        TypedQuery<Role> q = em.createNamedQuery(Role.GET_ALL_ROLES, Role.class);
        List<Role> result = q.getResultList();

        CustomLogger.logExit(this.getClass(), "getAllRoles", result.toString());
        return result;
    }

    /**
     * Get a list of all notifications stored in the database.
     *
     * @return : List of notifications, empty if there are no notifications in the database.
     */
    public List<Notification> getAllNotifications() {
        CustomLogger.logEnter(this.getClass(), "getAllNotifications", "");

        TypedQuery<Notification> q = em.createNamedQuery(Notification.GET, Notification.class);
        List<Notification> result = q.getResultList();

        CustomLogger.logExit(this.getClass(), "getAllNotifications", result.toString());
        return result;
    }

    /**
     * Returns a user entity with the matching email wrapped in an optional.
     * If none exist, returns an empty Optional Object
     *
     * @param email : String containing the email.
     * @return : Optional, containing a user entity.
     */
    public Optional<User> getUserByEmail(@NotNull String email) {
        CustomLogger.logEnter(this.getClass(), "getUserByEmail", email);

        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_EMAIL, User.class)
                .setParameter("email", email);
        Optional<User> result;
        try {
            result = Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            result = Optional.empty();
        }

        CustomLogger.logExit(this.getClass(), "getUserByEmail", result.toString());
        return result;
    }

    /**
     * TODO: de vazut alternative. Posibil sa nu mai trebuiasca.
     *
     * @param username
     * @return
     */
    public List<String> getUsernamesLike(String username) {
        CustomLogger.logEnter(this.getClass(), "getUsernameLike", username);

        Query q = em.createQuery("select u.username from User u where u.username like '" + username + "%'");
        List<String> result = q.getResultList();

        CustomLogger.logExit(this.getClass(), "getUsernameLike", result.toString());
        return result;
    }

    /**
     * Get the role having a specific type
     *
     * @param type
     * @return
     */
    public Role getRoleByType(String type) {
        Query q = em.createQuery("SELECT r FROM Role r WHERE r.type1='" + type + "'");
        return (Role) q.getSingleResult();
    }


}
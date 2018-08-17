package ro.msg.edu.jbugs.userManagement.persistence.dao;

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
     * @param user : user entity to be created, should not be null
     * @return : inserted user entity from database
     */
    public User createUser(@NotNull User user) {
        em.persist(user);
        em.flush();
        return user;
    }

    /**
     * Updates a user from the database.
     * @param user : user entity to be updated, should not be null
     * @return : updated user entity from database
     */
    public User updateUser(@NotNull User user) {
        return em.merge(user);
    }

    /**
     * Get a list of all users from the database.
     * @return : ResultList, empty if there are no users in the database.
     */
    public List<User> getAllUsers() {
        return em.createNamedQuery(User.GET_ALL_USERS, User.class)
                .getResultList();
    }


    /**
     * Returns a user entity with the matching username wrapped in an optional.
     * If none exist, returns an empty Optional Object
     * @param username : String containing the username.
     * @return : Optional, containing a user entity.
     */
    public Optional<User> getUserByUsername(@NotNull String username) {
        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_USERNAME,User.class)
                .setParameter("username",username);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }


    /**
     * Persists a user in the database.
     * @param role : role entity to be created, should not be null
     */
    public void createRole(@NotNull Role role) {
        em.persist(role);
    }

    /**
     * Removes a role from the database.
     * @param role : role entity to be removed, should not be null
     */
    public void removeRole(Role role) {
        em.remove(role);

    }

    /**
     * Updates a role in the database using the given Role entity.
     * @param role : role entity to be updated, should not be null
     * @return : returns the updated role entity
     */
    public Role updateRole(Role role) {
        em.merge(role);
        return role;
    }

    /**
     * TODO: nu cred ca avem nevoie de metoda asta - nu am mai facut-o frumoasa
     * Returns the role with the given id
     * @param id : id
     * @return : Role entity
     */
    public Role getRoleForId(long id) {
        Query q = em.createQuery("SELECT r FROM Role r WHERE r.id=" + id);
        return (Role) q.getSingleResult();
    }

    /**
     * Get a list of all roles stored in the database.
     * @return : List of Roles, empty if there are no roles in the database.
     */
    public List<Role> getAllRoles() {
        TypedQuery<Role> q = em.createNamedQuery(Role.GET_ALL_ROLES,Role.class);
        return q.getResultList();
    }


    /**
     * Returns a user entity with the matching email wrapped in an optional.
     * If none exist, returns an empty Optional Object
     * @param email : String containing the email.
     * @return : Optional, containing a user entity.
     */
    public Optional<User> getUserByEmail(@NotNull String email) {
        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_EMAIL, User.class)
                .setParameter("email",email);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /**
     * TODO: de vazut alternative. Posibil sa nu mai trebuiasca.
     * @param username
     * @return
     */
    public List<String> getUsernamesLike(String username) {
        Query q = em.createQuery("select u.username from User u where u.username like '" + username + "%'");
        return q.getResultList();
    }
}
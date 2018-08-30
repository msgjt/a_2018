package ro.msg.edu.jbugs.userManagement.persistence.dao;

import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class PermissionPersistenceManager {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;

    /**
     * Getter method for the permissions in the DB.
     *
     * @return a list of permissions, empty if no permissions found
     */
    public List<Permission> getAllPermissions() {
        Query query = em.createQuery("SELECT p FROM Permission p");
        return query.getResultList();
    }

}

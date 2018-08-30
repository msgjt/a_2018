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


    /*
     * UNNECESSARY METHODS. LEFT FOR FUTURE USE
     */
    /*

    public Permission getPermissionForId(long id) {
        Query query = em.createQuery("SELECT p FROM Permission p WHERE p.id=:id");
        query.setParameter("id",id);
        return (Permission) query.getSingleResult();
    }

    public List<Permission> getPermissionsForRole(Role role) {
        Query query = em.createQuery("SELECT r.permissions FROM Role r WHERE r=:role");
        query.setParameter("role",role);
        return query.getResultList();
    }


    public Permission createPermissionForRole(Role role, Permission permission) {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        em.merge(role);
        return permission;
    }


    public boolean removePermissionForRole(Role role, Permission permission) {
        em.persist(role);
        List<Permission> permissions = getPermissionsForRole(role);
        return permissions.remove(permission);

    }


    public boolean removeAllPermissionsForRole(Role role) {
        em.persist(role);
        List<Permission> permissions = getPermissionsForRole(role);
        permissions.clear();
        return true;
    }

    public Permission updatePermission(Permission permission) {
        em.merge(permission);
        return permission;
    }


    public boolean removePermissionById(long id) {
        Permission permission = getPermissionForId(id);
        if(permission == null)
            return false;
        em.remove(permission);
        return true;
    }

    public Permission addPermission(Permission permission) {
        logger.log(Level.ERROR,"aaaaaaaaaaa");
        em.persist(permission);
        return permission;
    }
*/
}

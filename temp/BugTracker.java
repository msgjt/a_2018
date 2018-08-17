package msg.ejb;


import edu.msg.ro.persistence.user.entity.User;

import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

@SessionScoped
public class BugTracker implements Serializable {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;

    public List<User> getAllUsers(){
        Query q = em.createQuery("SELECT u FROM User u");
        return q.getResultList();

    }

}

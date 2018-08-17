package msg.ejb;

import edu.msg.ro.persistence.user.entity.User;

import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class BugMgmt {

    @Inject
    private BugTracker bugTracker;

    public List<User> getAllUsers(){
        return bugTracker.getAllUsers();
    }
}

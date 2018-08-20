import resources.Authorization;
<<<<<<< HEAD
import resources.BugResource;
=======
import resources.RoleResource;
>>>>>>> fc54ddc27b835f3d3c4ae615b0396c4ec22ee740
import resources.UserResource;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest/")
public class UserManagementApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(UserResource.class);
<<<<<<< HEAD
        classes.add(BugResource.class);
=======
        classes.add(RoleResource.class);
>>>>>>> fc54ddc27b835f3d3c4ae615b0396c4ec22ee740
        classes.add(Authorization.class);
        return classes;
    }

}
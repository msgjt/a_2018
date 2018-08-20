import resources.Authorization;
import resources.BugResource;
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
        classes.add(BugResource.class);
        classes.add(Authorization.class);
        return classes;
    }

}
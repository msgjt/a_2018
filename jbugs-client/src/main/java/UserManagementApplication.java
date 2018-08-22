import resources.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest/")
public class UserManagementApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(UserResource.class);
        classes.add(BugResource.class);
        classes.add(RoleResource.class);
        classes.add(Authorization.class);
        classes.add(CaptchaResource.class);
        return classes;
    }

}
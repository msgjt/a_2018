import filters.BugsFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
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
        classes.add(PermissionsResource.class);
        classes.add(BusinessExceptionMapper.class);
        classes.add(Logout.class);
        classes.add(UserPermissions.class);
        classes.add(BugsFilter.class);
        classes.add(CheckedExceptionMapper.class);
        classes.add(LoggedIn.class);
        classes.add(NotificationResource.class);
        classes.add(MultiPartFeature.class);
        classes.add(UserProfileResource.class);
        classes.add(OldNotificationResource.class);
        classes.add(IsBugExportPdfOnServer.class);
        classes.add(IsBugCloseOnServer.class);
        return classes;

    }

}
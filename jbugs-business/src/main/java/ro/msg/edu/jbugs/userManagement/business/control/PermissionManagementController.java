package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.PermissionDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.PermissionDTOHelper;
import ro.msg.edu.jbugs.userManagement.persistence.dao.PermissionPersistenceManager;
import ro.msg.edu.jbugs.utils.CustomLogger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PermissionManagementController implements PermissionManagement {

    @EJB
    private PermissionPersistenceManager permissionPersistenceManager;

    @Override
    public List<PermissionDTO> getAllPermissions() {
        CustomLogger.logEnter(this.getClass(),"getAllPermissions","");

        List<PermissionDTO> result = permissionPersistenceManager.getAllPermissions().stream()
                .map(PermissionDTOHelper::fromEntity)
                .collect(Collectors.toList());

        CustomLogger.logExit(this.getClass(),"getAllPermissions",result.toString());
        return result;
    }
}

package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.utils.CustomLogger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RoleManagementController implements RoleManagement{


    @EJB
    private UserPersistenceManager userPersistenceManager;


    @Override
    public List<RoleDTO> getAllRoles() {
        CustomLogger.logEnter(this.getClass(),"getAllRoles","");

        List<RoleDTO> result = userPersistenceManager.getAllRoles().stream()
                .map(RoleDTOHelper::fromEntity)
                .collect(Collectors.toList());

        CustomLogger.logExit(this.getClass(),"getAllRoles",result.toString());
        return result;
    }

    @Override
    public RoleDTO updateRole(RoleDTO role){

        if( ! isValid(role) )
            throw new BusinessException(ExceptionCode.ROLE_VALIDATION_EXCEPTION);

        CustomLogger.logEnter(this.getClass(),"updateRole",role.toString());

        RoleDTO result = RoleDTOHelper.fromEntity(userPersistenceManager.updateRole(RoleDTOHelper.toEntity(role)));

        CustomLogger.logExit(this.getClass(),"updateRole",result.toString());
        return result;
    }

    private boolean isValid(RoleDTO role) {
        return role != null
                && role.getId() != null
                && role.getType() != null
                && role.getPermissions() != null;
    }

}

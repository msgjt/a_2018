package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;

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
        return userPersistenceManager.getAllRoles().stream()
                .map(RoleDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO updateRole(RoleDTO role) throws BusinessException{
        return RoleDTOHelper.fromEntity(userPersistenceManager.updateRole(RoleDTOHelper.toEntity(role)));
    }
}

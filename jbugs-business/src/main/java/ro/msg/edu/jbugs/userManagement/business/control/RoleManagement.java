package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.PermissionDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import java.util.List;

public interface RoleManagement {

    List<RoleDTO> getAllRoles();
    RoleDTO updateRole(RoleDTO role) throws BusinessException;


}

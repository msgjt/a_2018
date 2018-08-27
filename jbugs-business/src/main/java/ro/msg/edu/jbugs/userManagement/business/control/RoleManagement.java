package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;

import java.util.List;

public interface RoleManagement {

    List<RoleDTO> getAllRoles();
    RoleDTO updateRole(RoleDTO role);


}

package ro.msg.edu.jbugs.userManagement.business.dto;

import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class RoleDTOHelper {

    public static RoleDTO fromEntity(@NotNull Role role) {

        if (role == null || role.getPermissions() == null)
            throw new BusinessException(ExceptionCode.ROLE_VALIDATION_EXCEPTION);

        RoleDTO roleDTO = new RoleDTO();

        List<PermissionDTO> permissions = new ArrayList<>();
        role.getPermissions().forEach(p -> permissions.add(PermissionDTOHelper.fromEntity(p)));

        roleDTO.setId(role.getId());
        roleDTO.setType(role.getType1());
        roleDTO.setPermissions(permissions);

        return roleDTO;
    }

    public static Role toEntity(@NotNull RoleDTO roleDTO) {

        if (roleDTO == null || roleDTO.getPermissions() == null)
            throw new BusinessException(ExceptionCode.ROLE_VALIDATION_EXCEPTION);

        Role role = new Role();

        List<Permission> permissions = new ArrayList<>();
        roleDTO.getPermissions().forEach(p -> permissions.add(PermissionDTOHelper.toEntity(p)));

        role.setId(roleDTO.getId());
        role.setType1(roleDTO.getType());
        role.setPermissions(permissions);

        return role;
    }

}

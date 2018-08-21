package ro.msg.edu.jbugs.userManagement.business.dto;

import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleDTOHelper {

    public static RoleDTO fromEntity(Role role) {
        RoleDTO roleDTO = new RoleDTO();

        List<PermissionDTO> permissions = new ArrayList<>();
        role.getPermissions().forEach(p -> permissions.add(PermissionDTOHelper.fromEntity(p)));

        roleDTO.setId(role.getId());
        roleDTO.setType(role.getType());
        roleDTO.setPermissions(permissions);

        return roleDTO;
    }

    public static Role toEntity(RoleDTO roleDTO) {
        Role role = new Role();

        List<Permission> permissions = new ArrayList<>();
        roleDTO.getPermissions().forEach(p -> permissions.add(PermissionDTOHelper.toEntity(p)));

        role.setId(roleDTO.getId());
        role.setType(roleDTO.getType());
        role.setPermissions(permissions);

        return role;
    }

}

package ro.msg.edu.jbugs.userManagement.business.dto;

import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;

public class PermissionDTOHelper {

    public static PermissionDTO fromEntity(Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();

        permissionDTO.setDescription(permission.getDescription());
        permissionDTO.setType(permission.getType());

        return permissionDTO;
    }

    public static Permission toEntity(PermissionDTO permissionDTO) {
        Permission permission = new Permission();

        permission.setDescription(permissionDTO.getDescription());
        permission.setType(permissionDTO.getType());

        return permission;
    }

}

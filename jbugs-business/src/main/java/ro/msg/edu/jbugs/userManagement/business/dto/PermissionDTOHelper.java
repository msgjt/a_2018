package ro.msg.edu.jbugs.userManagement.business.dto;

import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import javax.validation.constraints.NotNull;

public class PermissionDTOHelper {


    public static PermissionDTO fromEntity(@NotNull Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();

        permissionDTO.setId(permission.getId());
        permissionDTO.setDescription(permission.getDescription());
        permissionDTO.setType(permission.getType2());

        return permissionDTO;
    }

    public static Permission toEntity(@NotNull PermissionDTO permissionDTO) {
        Permission permission = new Permission();

        permission.setId(permissionDTO.getId());
        permission.setDescription(permissionDTO.getDescription());
        permission.setType2(permissionDTO.getType());

        return permission;
    }

}

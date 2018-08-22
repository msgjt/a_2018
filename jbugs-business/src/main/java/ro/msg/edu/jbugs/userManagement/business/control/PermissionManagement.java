package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.PermissionDTO;

import java.util.List;

public interface PermissionManagement {
    List<PermissionDTO> getAllPermissions();
}

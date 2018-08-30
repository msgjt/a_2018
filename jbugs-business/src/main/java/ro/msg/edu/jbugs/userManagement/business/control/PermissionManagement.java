package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.PermissionDTO;

import java.util.List;

public interface PermissionManagement {

    /**
     * Creates a list of all the permissions from the persistence layer.
     * METHOD LOGGED WITH CustomLogger
     *
     * @return the list of all the permissions, empty if no permissions found.
     */
    List<PermissionDTO> getAllPermissions();

}

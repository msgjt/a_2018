package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;

import java.util.List;

public interface RoleManagement {


    /**
     * Returns a list of all the roles from the persistence layer.
     * METHOD LOGGED WITH CustomLogger
     *
     * @return the list of all the roles, empty if no roles found.
     */
    List<RoleDTO> getAllRoles();


    /**
     * Updates a role by receiving a roleDTO, which it will validate and persist in the DB.
     * METHOD LOGGED WITH CustomLogger
     *
     * @param roleDTO an object of type RoleDTO or null
     * @return the resulted roleDTO persisted in the DB (its id will be set)
     */
    RoleDTO updateRole(RoleDTO roleDTO);


}

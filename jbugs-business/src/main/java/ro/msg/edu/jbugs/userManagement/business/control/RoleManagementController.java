package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.validator.RoleValidator;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RoleManagementController implements RoleManagement {




    @EJB
    private UserPersistenceManager userPersistenceManager;

    @EJB
    private RoleValidator roleValidator;




    /**
     * Returns a list of all the roles from the persistence layer.
     * METHOD LOGGED WITH CustomLogger
     *
     * @return the list of all the roles, empty if no roles found.
     */
    @Override
    public List<RoleDTO> getAllRoles() {
        CustomLogger.logEnter(this.getClass(), "getAllRoles", "");

        List<RoleDTO> result = userPersistenceManager.getAllRoles().stream()
                .map(RoleDTOHelper::fromEntity)
                .collect(Collectors.toList());

        CustomLogger.logExit(this.getClass(), "getAllRoles", result.toString());
        return result;
    }

    /**
     * Updates a role by receiving a roleDTO, which it will validate and persist in the DB.
     * METHOD LOGGED WITH CustomLogger
     *
     * @param roleDTO an object of type RoleDTO or null
     * @return the resulted roleDTO persisted in the DB (its id will be set)
     */
    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {
        CustomLogger.logEnter(this.getClass(), "updateRole", roleDTO.toString());

        roleValidator.validateUpdate(roleDTO);
        RoleDTO result = RoleDTOHelper.fromEntity(userPersistenceManager.updateRole(RoleDTOHelper.toEntity(roleDTO)));

        CustomLogger.logExit(this.getClass(), "updateRole", result.toString());
        return result;
    }

}

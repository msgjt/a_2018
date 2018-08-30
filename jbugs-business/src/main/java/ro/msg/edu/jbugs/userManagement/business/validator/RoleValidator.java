package ro.msg.edu.jbugs.userManagement.business.validator;

import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.DetailedExceptionCode;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.shared.business.validator.BaseValidator;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;

import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Stateless
public class RoleValidator extends BaseValidator {

    /**
     * Method returns the validation map associated to an object of type "Role"
     *
     * @param roleDTO not null
     * @return a map
     */
    protected Map<DetailedExceptionCode, Boolean> getCreateValidationMap(@NotNull RoleDTO roleDTO) {
        CustomLogger.logEnter(this.getClass(), "getCreateValidationMap", roleDTO.toString());

        Map<DetailedExceptionCode, Boolean> result = new HashMap<DetailedExceptionCode, Boolean>() {{
            put(DetailedExceptionCode.ROLE_TYPE_NULL, roleDTO.getType() == null);
            put(DetailedExceptionCode.ROLE_PERMISSIONS_NULL, roleDTO.getPermissions() == null);
        }};

        CustomLogger.logExit(this.getClass(), "getCreateValidationMap", result.toString());
        return result;
    }

    /**
     * Method returns the validation map associated to an object of type "Role"
     *
     * @param roleDTO not null
     * @return a map
     */
    protected Map<DetailedExceptionCode, Boolean> getUpdateValidationMap(@NotNull RoleDTO roleDTO) {
        CustomLogger.logEnter(this.getClass(), "getUpdateValidationMap", roleDTO.toString());

        Map<DetailedExceptionCode, Boolean> result = new HashMap<DetailedExceptionCode, Boolean>() {{
            put(DetailedExceptionCode.ROLE_PERMISSIONS_NULL, roleDTO.getPermissions() == null);
        }};

        CustomLogger.logExit(this.getClass(), "getUpdateValidationMap", result.toString());
        return result;
    }

    /**
     * BaseValidator method required for a role to be created.
     *
     * @param roleDTO the roleDTO to be validated, can be null
     * @throws BusinessException if the roleDTO is null or if any of its validated fields are null
     */
    public void validateCreate(RoleDTO roleDTO) {
        CustomLogger.logEnter(this.getClass(), "validateRoleForCreation", String.valueOf(roleDTO));

        if (roleDTO == null) {
            CustomLogger.logExit(this.getClass(), "validateRoleForUpdate",
                    ExceptionCode.ROLE_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.ROLE_NULL);
            throw new BusinessException(ExceptionCode.ROLE_VALIDATION_EXCEPTION, DetailedExceptionCode.ROLE_NULL);
        }

        Map<DetailedExceptionCode, Boolean> validationMap = getCreateValidationMap(roleDTO);
        Stack<DetailedExceptionCode> validationMessageStack = getValidationMessage(validationMap);
        if (!validationMessageStack.empty()) {
            CustomLogger.logException(this.getClass(), "validateRoleForCreation",
                    ExceptionCode.ROLE_VALIDATION_EXCEPTION + " " + validationMessageStack.toString());
            throw new BusinessException(ExceptionCode.ROLE_VALIDATION_EXCEPTION, validationMessageStack);
        }
        CustomLogger.logExit(this.getClass(), "validateRoleForCreation", "OK");
    }

    /**
     * BaseValidator method required for a role to be updated.
     *
     * @param roleDTO the roleDTO to be validated, can be null
     * @throws BusinessException if the roleDTO is null or if any of its validated fields are null
     */
    public void validateUpdate(RoleDTO roleDTO) throws BusinessException {
        CustomLogger.logEnter(this.getClass(), "validateRoleForUpdate", String.valueOf(roleDTO));

        if (roleDTO == null) {
            CustomLogger.logExit(this.getClass(), "validateRoleForUpdate",
                    ExceptionCode.ROLE_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.ROLE_NULL);
            throw new BusinessException(ExceptionCode.ROLE_VALIDATION_EXCEPTION, DetailedExceptionCode.ROLE_NULL);
        }

        Map<DetailedExceptionCode, Boolean> validationMap = getUpdateValidationMap(roleDTO);
        validationMap.put(DetailedExceptionCode.ROLE_ID_NULL, roleDTO.getId() == null);
        Stack<DetailedExceptionCode> validationMessageStack = getValidationMessage(validationMap);
        if (!validationMessageStack.empty()) {
            CustomLogger.logException(this.getClass(), "validateRoleForUpdate",
                    ExceptionCode.ROLE_VALIDATION_EXCEPTION + " " + validationMessageStack.toString());
            throw new BusinessException(ExceptionCode.ROLE_VALIDATION_EXCEPTION, validationMessageStack);
        }

        CustomLogger.logExit(this.getClass(), "validateRoleForUpdate", "OK");
    }


}

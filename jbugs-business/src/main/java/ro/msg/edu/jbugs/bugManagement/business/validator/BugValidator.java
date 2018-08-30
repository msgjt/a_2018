package ro.msg.edu.jbugs.bugManagement.business.validator;

import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.DetailedExceptionCode;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.shared.business.validator.BaseValidator;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;

import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
public class BugValidator extends BaseValidator {

    /**
     * Method returns the validation map associated to an object of type "Bug" in the creation context.
     *
     * @param bugDTO not null
     * @return a map
     */
    private Map<DetailedExceptionCode, Boolean> getCreateValidationMap(@NotNull BugDTO bugDTO) {
        CustomLogger.logEnter(BugValidator.class, "getCreateValidationMap", bugDTO.toString());

        Map<DetailedExceptionCode, Boolean> result = new HashMap<DetailedExceptionCode, Boolean>() {{
            put(DetailedExceptionCode.BUG_TITLE_NULL, bugDTO.getTitle() == null);
            put(DetailedExceptionCode.BUG_CREATED_BY_NULL, bugDTO.getCreatedBy() == null);
            put(DetailedExceptionCode.BUG_VERSION_NULL, bugDTO.getVersion() == null);
            put(DetailedExceptionCode.BUG_SEVERITY_NULL, bugDTO.getSeverity() == null);
            put(DetailedExceptionCode.BUG_DESCRIPTION_TOO_SHORT, bugDTO.getDescription().length() < 250);
            put(DetailedExceptionCode.BUG_VERSION_NOT_VALID, !isValidVersion(bugDTO.getVersion()));
        }};

        CustomLogger.logExit(BugValidator.class, "getCreateValidationMap", result.toString());
        return result;
    }

    /**
     * Method returns the validation map associated to an object of type "Bug" in the update context.
     *
     * @param bugDTO not null
     * @return a map
     */
    private Map<DetailedExceptionCode, Boolean> getUpdateValidationMap(@NotNull BugDTO bugDTO) {
        CustomLogger.logEnter(BugValidator.class, "getUpdateValidationMap", bugDTO.toString());

        Map<DetailedExceptionCode, Boolean> result = new HashMap<DetailedExceptionCode, Boolean>() {{
            put(DetailedExceptionCode.BUG_TITLE_NULL, bugDTO.getTitle() == null);
            put(DetailedExceptionCode.BUG_ASSIGNED_TO_NULL, bugDTO.getAssignedTo() == null);
            put(DetailedExceptionCode.BUG_TARGET_DATE_NULL, bugDTO.getTargetDate() == null);
            put(DetailedExceptionCode.BUG_FIXED_VERSION_NULL, bugDTO.getFixedVersion() == null);
            put(DetailedExceptionCode.BUG_VERSION_NULL, bugDTO.getVersion() == null);
            put(DetailedExceptionCode.BUG_SEVERITY_NULL, bugDTO.getSeverity() == null);
            put(DetailedExceptionCode.BUG_STATUS_NULL, bugDTO.getStatus() == null);
        }};

        CustomLogger.logExit(BugValidator.class, "getUpdateValidationMap", result.toString());
        return result;
    }


    /**
     * BaseValidator method required for a bug to be created.
     *
     * @param bugDTO the bugDTO to be validated, can be null
     * @throws BusinessException if the bugDTO is null or if any of its validated fields are null
     */
    public void validateCreate(BugDTO bugDTO) {
        CustomLogger.logEnter(this.getClass(), "validateBugForCreation", String.valueOf(bugDTO));

        if (bugDTO == null) {
            CustomLogger.logExit(this.getClass(), "validateBugForUpdate",
                    ExceptionCode.BUG_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.BUG_NULL);
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION, DetailedExceptionCode.BUG_NULL);
        }

        Map<DetailedExceptionCode, Boolean> validationMap = getCreateValidationMap(bugDTO);
        Stack<DetailedExceptionCode> validationMessageStack = getValidationMessage(validationMap);
        if (!validationMessageStack.empty()) {
            CustomLogger.logException(this.getClass(), "validateBugForCreation",
                    ExceptionCode.BUG_VALIDATION_EXCEPTION + " " + validationMessageStack.toString());
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION, validationMessageStack);
        }
        CustomLogger.logExit(this.getClass(), "validateBugForCreation", "OK");
    }

    /**
     * BaseValidator method required for a bug to be updated.
     *
     * @param bugDTO the bugDTO to be validated, can be null
     * @throws BusinessException if the bugDTO is null or if any of its validated fields are null
     */
    public void validateUpdate(BugDTO bugDTO) throws BusinessException {
        CustomLogger.logEnter(this.getClass(), "validateBugForUpdate", String.valueOf(bugDTO));

        if (bugDTO == null) {
            CustomLogger.logExit(this.getClass(), "validateBugForUpdate",
                    ExceptionCode.BUG_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.BUG_NULL);
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION, DetailedExceptionCode.BUG_NULL);
        }

        Map<DetailedExceptionCode, Boolean> validationMap = getUpdateValidationMap(bugDTO);
        validationMap.put(DetailedExceptionCode.BUG_ID_NULL, bugDTO.getId() == null);
        Stack<DetailedExceptionCode> validationMessageStack = getValidationMessage(validationMap);
        if (!validationMessageStack.empty()) {
            CustomLogger.logException(this.getClass(), "validateBugForUpdate",
                    ExceptionCode.BUG_VALIDATION_EXCEPTION + " " + validationMessageStack.toString());
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION, validationMessageStack);
        }

        CustomLogger.logExit(this.getClass(), "validateBugForUpdate", "OK");
    }

    /**
     * Checks a version to a pattern.
     *
     * @param version the bug field to be validated
     * @return true if the bug version conforms to the given pattern, false otherwise
     */
    public boolean isValidVersion(String version) {
        if (version == null)
            return false;
        Pattern VALID_VERSION_REGEX =
                Pattern.compile("^\\w+(\\.\\w*)*$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_VERSION_REGEX.matcher(version);
        return matcher.find();
    }


}

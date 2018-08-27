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
public class BugValidator extends BaseValidator{

    /**
     * Method returns the validation map associated to an object of type "Bug"
     * @param bugDTO not null
     * @return a map
     */
    private Map<DetailedExceptionCode,Boolean> getValidationMap(@NotNull BugDTO bugDTO) {
        CustomLogger.logEnter(BugValidator.class,"getValidationMap",bugDTO.toString());

        Map<DetailedExceptionCode,Boolean> result = new HashMap<DetailedExceptionCode,Boolean>() {{
            put(DetailedExceptionCode.BUG_TITLE_NULL,           bugDTO.getTitle() == null);
            put(DetailedExceptionCode.BUG_CREATED_BY_NULL,      bugDTO.getCreatedBy() == null);
            put(DetailedExceptionCode.BUG_VERSION_NULL,         bugDTO.getVersion() == null);
            put(DetailedExceptionCode.BUG_SEVERITY_NULL,        bugDTO.getSeverity() == null);
            put(DetailedExceptionCode.BUG_DESCRIPTION_TOO_SHORT,bugDTO.getDescription().length() < 250);
            put(DetailedExceptionCode.BUG_VERSION_NOT_VALID,    !isValidVersion(bugDTO.getVersion()));
        }};

        CustomLogger.logExit(BugValidator.class,"getValidationMap",result.toString());
        return result;
    }

    /**
     * BaseValidator method required for a bug to be created.
     * @param bugDTO the bugDTO to be validated, can be null
     * @throws BusinessException if the bugDTO is null or if any of its validated fields are null
     */
    public void validateCreate(BugDTO bugDTO) {
        CustomLogger.logEnter(this.getClass(),"validateBugForCreation",String.valueOf(bugDTO));

        if(bugDTO == null) {
            CustomLogger.logExit(this.getClass(),"validateBugForUpdate",
                    ExceptionCode.BUG_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.BUG_NULL);
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,DetailedExceptionCode.BUG_NULL);
        }

        Map<DetailedExceptionCode,Boolean> validationMap = getValidationMap(bugDTO);
        Stack<DetailedExceptionCode> validationMessageStack = getValidationMessage(validationMap);
        if( ! validationMessageStack.empty() ) {
            CustomLogger.logException(this.getClass(),"validateBugForCreation",
                    ExceptionCode.BUG_VALIDATION_EXCEPTION + " " + validationMessageStack.toString());
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,validationMessageStack);
        }
        CustomLogger.logExit(this.getClass(),"validateBugForCreation","OK");
    }

    /**
     * BaseValidator method required for a bug to be updated.
     * @param bugDTO the bugDTO to be validated, can be null
     * @throws BusinessException if the bugDTO is null or if any of its validated fields are null
     */
    public void validateUpdate(BugDTO bugDTO) throws BusinessException {
        CustomLogger.logEnter(this.getClass(),"validateBugForUpdate",String.valueOf(bugDTO));

        if(bugDTO == null) {
            CustomLogger.logExit(this.getClass(),"validateBugForUpdate",
                    ExceptionCode.BUG_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.BUG_NULL);
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,DetailedExceptionCode.BUG_NULL);
        }

        Map<DetailedExceptionCode,Boolean> validationMap = getValidationMap(bugDTO);
        validationMap.put(DetailedExceptionCode.BUG_ID_NULL,bugDTO.getId() == null);
        Stack<DetailedExceptionCode> validationMessageStack = getValidationMessage(validationMap);
        if( ! validationMessageStack.empty() ) {
            CustomLogger.logException(this.getClass(),"validateBugForUpdate",
                    ExceptionCode.BUG_VALIDATION_EXCEPTION + " " + validationMessageStack.toString());
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,validationMessageStack);
        }

        CustomLogger.logExit(this.getClass(),"validateBugForUpdate","OK");
    }


    public boolean isValidVersion(String version){
        if( version == null )
            return false;
        Pattern VALID_VERSION_REGEX =
                Pattern.compile("^\\w+(\\.\\w*)*$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_VERSION_REGEX.matcher(version);
        return matcher.find();
    }


}

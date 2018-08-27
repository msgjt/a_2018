package ro.msg.edu.jbugs.userManagement.business.validator;

import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.DetailedExceptionCode;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.shared.business.validator.BaseValidator;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;

import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class UserValidator extends BaseValidator {


    /**
     * Method returns the validation map associated to an object of type "UserDTO"
     * @param userDTO not null
     * @return a map
     */
    private Map<DetailedExceptionCode,Boolean> getCreateValidationMap(@NotNull UserDTO userDTO) {
        CustomLogger.logEnter(UserValidator.class,"getCreateValidationMap",userDTO.toString());

        Map<DetailedExceptionCode,Boolean> result = new HashMap<DetailedExceptionCode,Boolean>() {{
            put(DetailedExceptionCode.USER_FIRSTNAME_NULL, userDTO.getFirstName() == null);
            put(DetailedExceptionCode.USER_LASTNAME_NULL,userDTO.getLastName() == null);
            put(DetailedExceptionCode.USER_PASSWORD_NULL,userDTO.getPassword() == null);
            put(DetailedExceptionCode.USER_PHONE_NUMBER_NULL,userDTO.getPhoneNumber() == null);
            put(DetailedExceptionCode.USER_EMAIL_NULL,userDTO.getEmail() == null);
            put(DetailedExceptionCode.USER_PHONE_NUMBER_NOT_VALID,!isValidPhoneNumber(userDTO.getPhoneNumber()));
            put(DetailedExceptionCode.USER_EMAIL_NOT_VALID,!isValidEmail(userDTO.getEmail()));
        }};

        CustomLogger.logExit(UserValidator.class,"getCreateValidationMap",result.toString());
        return result;
    }


    /**
     * Method returns the validation map associated to an object of type "UserDTO"
     * @param userDTO not null
     * @return a map
     */
    private Map<DetailedExceptionCode,Boolean> getUpdateValidationMap(@NotNull UserDTO userDTO) {
        CustomLogger.logEnter(UserValidator.class,"getUpdateValidationMap",userDTO.toString());

        Map<DetailedExceptionCode,Boolean> result = new HashMap<DetailedExceptionCode,Boolean>() {{
            put(DetailedExceptionCode.USER_FIRSTNAME_NULL, userDTO.getFirstName() == null);
            put(DetailedExceptionCode.USER_LASTNAME_NULL,userDTO.getLastName() == null);
            put(DetailedExceptionCode.USER_PHONE_NUMBER_NULL,userDTO.getPhoneNumber() == null);
            put(DetailedExceptionCode.USER_EMAIL_NULL,userDTO.getEmail() == null);
            put(DetailedExceptionCode.USER_PHONE_NUMBER_NOT_VALID,!isValidPhoneNumber(userDTO.getPhoneNumber()));
            put(DetailedExceptionCode.USER_EMAIL_NOT_VALID,!isValidEmail(userDTO.getEmail()));
            put(DetailedExceptionCode.USER_ID_NULL,userDTO.getId() == null);
        }};

        CustomLogger.logExit(UserValidator.class,"getUpdateValidationMap",result.toString());
        return result;
    }


    /**
     * BaseValidator method required for a userDTO to be created.
     * @param userDTO the userDTO to be validated, can be null
     * @throws BusinessException if the userDTO is null or if any of its validated fields are null
     */
    public void validateCreate(UserDTO userDTO) {
        CustomLogger.logEnter(this.getClass(),"validateUserDTOForCreation",String.valueOf(userDTO));

        if(userDTO == null) {
            CustomLogger.logExit(this.getClass(),"validateUserDTOForUpdate",
                    ExceptionCode.USER_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.USER_NULL);
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION,DetailedExceptionCode.USER_NULL);
        }

        Map<DetailedExceptionCode,Boolean> validationMap = getCreateValidationMap(userDTO);
        Stack<DetailedExceptionCode> validationMessageStack = getValidationMessage(validationMap);
        if( ! validationMessageStack.empty() ) {
            CustomLogger.logException(this.getClass(),"validateUserDTOForCreation",
                    ExceptionCode.USER_VALIDATION_EXCEPTION + " " + validationMessageStack.toString());
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION,validationMessageStack);
        }
        CustomLogger.logExit(this.getClass(),"validateUserDTOForCreation","OK");
    }

    /**
     * BaseValidator method required for a userDTO to be updated.
     * @param userDTO the userDTO to be validated, can be null
     * @throws BusinessException if the userDTO is null or if any of its validated fields are null
     */
    public void validateUpdate(UserDTO userDTO) throws BusinessException {
        CustomLogger.logEnter(this.getClass(),"validateUserDTOForUpdate",String.valueOf(userDTO));

        if(userDTO == null) {
            CustomLogger.logExit(this.getClass(),"validateUserDTOForUpdate",
                    ExceptionCode.USER_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.USER_NULL);
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION,DetailedExceptionCode.USER_NULL);
        }

        Map<DetailedExceptionCode,Boolean> validationMap = getUpdateValidationMap(userDTO);
        Stack<DetailedExceptionCode> validationMessageStack = getValidationMessage(validationMap);
        if( ! validationMessageStack.empty() ) {
            CustomLogger.logException(this.getClass(),"validateUserDTOForUpdate",
                    ExceptionCode.USER_VALIDATION_EXCEPTION + " " + validationMessageStack.toString());
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION,validationMessageStack);
        }

        CustomLogger.logExit(this.getClass(),"validateUserDTOForUpdate","OK");
    }

    /**
     * Checks if the given number is a valid german or romanian phone number
     * @param phoneNumber .
     * @return true if valid, false if not or if it is null
     */
    private boolean isValidPhoneNumber(String phoneNumber) {

        if( phoneNumber == null )
            return false;

        final Pattern VALID_PHONE_ADDRESS_REGEX_GERMANY =
                Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher_ger = VALID_PHONE_ADDRESS_REGEX_GERMANY.matcher(phoneNumber);
        final Pattern VALID_PHONE_ADDRESS_REGEX_ROMANIA =
                Pattern.compile("^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher_ro = VALID_PHONE_ADDRESS_REGEX_ROMANIA.matcher(phoneNumber);

        return matcher_ger.find() || matcher_ro.find();
    }

    /**
     * Validates the email to the required pattern. (*.@msggroup.com)
     * @param email .
     * @return true if the email respects the pattern, false if not or if it is null
     */
    private boolean isValidEmail(String email) {
        CustomLogger.logEnter(this.getClass(),"isValidEmail",email);

        if( email == null )
            return false;

        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@msggroup.com$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        boolean result = matcher.find();

        CustomLogger.logExit(this.getClass(),"isValidEmail",String.valueOf(result));
        return result;
    }

    /**
     * Validates the username for null value.
     * @param username .
     */
    public void validateUsername(String username){
        if(username == null)
            throw new BusinessException(
                    ExceptionCode.USER_VALIDATION_EXCEPTION,
                    DetailedExceptionCode.USER_USERNAME_NULL
            );
    }

}
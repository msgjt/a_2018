package ro.msg.edu.jbugs.shared.business.validator;

import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.DetailedExceptionCode;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;

import javax.ejb.Stateless;
import java.util.Map;
import java.util.Stack;

@Stateless
public class BaseValidator {
    /**
     * Creates a validation output message from a given validation map.
     * @param validationMap the map to be validated.
     * @return a String containing each validation fails separated by commas.
     */
    protected Stack<DetailedExceptionCode> getValidationMessage(Map<DetailedExceptionCode,Boolean> validationMap) {
        CustomLogger.logEnter(BaseValidator.class,"getValidationMessage",validationMap.toString());

        Stack<DetailedExceptionCode> message = new Stack<>();
        validationMap.keySet()
                .stream()
                .filter(key -> validationMap.get(key).equals(true))
                .forEach(message::push);

        CustomLogger.logExit(BaseValidator.class,"getValidationMessage",message.toString());
        return message;
    }

    public void validateId(Long id){
        if(id == null)
            throw new BusinessException(
                    ExceptionCode.USER_VALIDATION_EXCEPTION,
                    DetailedExceptionCode.USER_ID_NULL
            );
    }
}

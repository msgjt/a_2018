package ro.msg.edu.jbugs.shared.business.exceptions;

import javax.json.Json;
import java.util.Stack;
import java.util.stream.Collectors;

public class BusinessException extends RuntimeException {

    private ExceptionCode exceptionCode;
    private DetailedExceptionCode detailedExceptionCode;

    public BusinessException() {
    }

    /**
     * Constructor for detailed information of the exception.
     *
     * @param exceptionCode main exception code (will describe which controller threw the exception)
     * @param detailedExceptionCode detailed exception code (will describe the reasoning behind the controller fail)
     */
    public BusinessException(ExceptionCode exceptionCode, DetailedExceptionCode detailedExceptionCode) {
        super(new String("{" +
                "\"id\":\"" + Integer.sum(exceptionCode.id,detailedExceptionCode.id) + "\"," +
                "\"type\":\"" + exceptionCode.message + "\"," +
                "\"details\":[" +
                "{\"detail\":\"" + detailedExceptionCode.message + "\"}" +
                "]" +
                "}"));

        this.exceptionCode = exceptionCode;
        this.detailedExceptionCode = detailedExceptionCode;
    }

    /**
     * Constructor for detailed information of the exception with a detailed stack trace.
     *
     * @param exceptionCode main exception code (will describe which controller threw the exception)
     * @param detailedStack stack trace of multiple exception codes
     */
    public BusinessException(ExceptionCode exceptionCode, Stack<DetailedExceptionCode> detailedStack) {
        super(new String("{" +
                "\"id\":\"" + Integer.sum(exceptionCode.id,detailedStack.peek().id) + "\"," +
                "\"type\":\"" + exceptionCode.message + "\"," +
                "\"details\":[" +
                "{\"detail\":\"" + detailedStack.peek().message + "\"}" +
                "]" +
                "}"));
    }

    /**
     * Constructor for simple information of the exception.
     * @param exceptionCode will describe which manager threw the exception
     */
    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public BusinessException(String message, ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage() + "," + message);
        this.exceptionCode = exceptionCode;
    }

    public BusinessException(String message, Throwable cause, ExceptionCode exceptionCode) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }

    public BusinessException(Throwable cause, ExceptionCode exceptionCode) {
        super(cause);
        this.exceptionCode = exceptionCode;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionCode exceptionCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public DetailedExceptionCode getDetailedExceptionCode() {
        return detailedExceptionCode;
    }

}

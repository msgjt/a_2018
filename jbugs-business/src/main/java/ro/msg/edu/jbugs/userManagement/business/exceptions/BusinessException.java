package ro.msg.edu.jbugs.userManagement.business.exceptions;

import ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode;

public class BusinessException extends Exception {

    ExceptionCode exceptionCode;

    public BusinessException() {
    }
    public BusinessException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
    public BusinessException(String message,ExceptionCode exceptionCode ) {
        super(message);
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

    public void setExceptionCode(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}

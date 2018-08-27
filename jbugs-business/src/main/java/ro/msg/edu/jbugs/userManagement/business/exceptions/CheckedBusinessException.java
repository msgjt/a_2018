package ro.msg.edu.jbugs.userManagement.business.exceptions;

public class CheckedBusinessException extends Exception {
    ExceptionCode exceptionCode;

    public CheckedBusinessException() {
        super();
    }

    public CheckedBusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public CheckedBusinessException(String message) {
        super(message);
    }

    public CheckedBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckedBusinessException(Throwable cause) {
        super(cause);
    }

    protected CheckedBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

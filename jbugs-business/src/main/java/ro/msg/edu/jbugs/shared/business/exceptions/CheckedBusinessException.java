package ro.msg.edu.jbugs.shared.business.exceptions;


public class CheckedBusinessException extends Exception {

    private ExceptionCode exceptionCode;

    /**
     * Constructor for detailed information of the exception.
     *
     * @param exceptionCode main exception code (will describe which controller threw the exception)
     * @param detailedExceptionCode detailed exception code (will describe the reasoning behind the controller fail)
     */
    public CheckedBusinessException(ExceptionCode exceptionCode, DetailedExceptionCode detailedExceptionCode) {
        super("{id=" + exceptionCode.id + detailedExceptionCode.id
                + ", type=" + exceptionCode.message
                + ", details={" + detailedExceptionCode.message + "}}");
        this.exceptionCode = exceptionCode;
        DetailedExceptionCode detailedExceptionCode1 = detailedExceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
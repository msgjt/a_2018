package ro.msg.edu.jbugs.shared.business.exceptions;

import javax.ws.rs.core.Response;
import java.util.Stack;
import java.util.stream.Collectors;

public class CheckedBusinessException extends Exception {

    ExceptionCode exceptionCode;
    DetailedExceptionCode detailedExceptionCode;

    public CheckedBusinessException() {
    }

    public CheckedBusinessException(ExceptionCode exceptionCode, DetailedExceptionCode detailedExceptionCode) {
        super("{id=" + exceptionCode.id + detailedExceptionCode.id
                + ", type=" + exceptionCode.message
                + ", details={" + detailedExceptionCode.message + "}}");
        this.exceptionCode = exceptionCode;
        this.detailedExceptionCode = detailedExceptionCode;
    }

    public CheckedBusinessException(ExceptionCode exceptionCode, Stack<DetailedExceptionCode> detailedStack) {
        super(detailedStack.stream()
                .map(dExc -> "{id=" + exceptionCode.id + dExc.id
                        + ", type=" + exceptionCode.message
                        + ", details={" + dExc.message + "}}"
                )
                .collect(Collectors.toList())
                .toString());
    }

    public CheckedBusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public CheckedBusinessException(String message, ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage() + "," + message);
        this.exceptionCode = exceptionCode;
    }

    public CheckedBusinessException(String message, Throwable cause, ExceptionCode exceptionCode) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }

    public CheckedBusinessException(Throwable cause, ExceptionCode exceptionCode) {
        super(cause);
        this.exceptionCode = exceptionCode;
    }

    public CheckedBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionCode exceptionCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public CheckedBusinessException(Response response) {

    }

    public CheckedBusinessException(Throwable cause, Response response) {

    }

    public CheckedBusinessException(Response.Status status) {

    }

    public CheckedBusinessException(Throwable cause, Response.Status status) {

    }
}
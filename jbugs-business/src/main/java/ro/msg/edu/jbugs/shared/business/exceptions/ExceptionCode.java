package ro.msg.edu.jbugs.shared.business.exceptions;

/**
 * Provides exception codes and description.
 */
public enum ExceptionCode {
    USER_VALIDATION_EXCEPTION(1000, "USER_VALIDATION_EXCEPTION"),
    ROLE_VALIDATION_EXCEPTION(2000, "ROLE_VALIDATION_EXCEPTION"),
    BUG_VALIDATION_EXCEPTION(3000,"BUG_VALIDATION_EXCEPTION");
    int id;
    String message;

    ExceptionCode(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

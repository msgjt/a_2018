package ro.msg.edu.jbugs.userManagement.business.exceptions;

/**
 * Provides exception codes and description.
 */
public enum ExceptionCode {
    USER_VALIDATION_EXCEPTION(1000, "Validation Exception"),
    EMAIL_EXISTS_ALREADY(1001, "Email already exists Exception"),
    PASSWORD_NOT_VALID(1002, "Password not valid."),
    USERNAME_NOT_VALID(1003, "Username not valid");
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

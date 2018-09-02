package ro.msg.edu.jbugs.shared.business.exceptions;

/**
 * Provides detailed information of the reasoning behind the exception.
 */
public enum DetailedExceptionCode {
    USER_NULL(1, "USER_NULL"),
    USER_REQUIRED_FIELD_NULL(100, "USER_REQUIRED_FIELD_NULL"),
    USER_FIRSTNAME_NULL(101, "USER_FIRSTNAME_NULL"),
    USER_LASTNAME_NULL(102, "USER_LASTNAME_NULL"),
    USER_PASSWORD_NULL(103, "USER_PASSWORD_NULL"),
    USER_PHONE_NUMBER_NULL(104, "USER_PHONE_NUMBER_NULL"),
    USER_EMAIL_NULL(105, "USER_EMAIL_NULL"),
    USER_IS_ACTIVE_NULL(106, "USER_IS_ACTIVE_NULL"),
    USER_ROLES_NULL(107, "USER_ROLES_NULL"),
    USER_ID_NULL(108, "USER_ID_NULL"),
    USER_USERNAME_NULL(109, "USER_USERNAME_NULL"),
    USER_COMPLEX_VALIDATION_FAIL(200, "USER_COMPLEX_VALIDATION_FAIL"),
    USER_PHONE_NUMBER_NOT_VALID(201, "USER_PHONE_NUMBER_NOT_VALID"),
    USER_EMAIL_NOT_VALID(202, "USER_EMAIL_NOT_VALID"),
    USER_PASSWORD_NOT_VALID(203, "USER_PASSWORD_NOT_VALID"),
    USER_DUPLICATE_EMAIL(204, "USER_DUPLICATE_EMAIL"),
    USER_ROLES_NOT_VALID(205, "USER_ROLES_NOT_VALID"),
    USER_LOGIN_FAILED_FIVE_TIMES(206, "USER_LOGIN_FAILED_FIVE_TIMES"),
    USER_DISABLED(207, "USER_DISABLED"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND"),
    USER_COULD_NOT_BE_DISABLED(203 ,"USER_COULD_NOT_BE_DISABLED"),


    ROLE_NULL(1, "ROLE_NULL"),
    ROLE_REQUIRED_FIELD_NULL(100, "ROLE_REQUIRED_FIELD_NULL"),
    ROLE_TYPE_NULL(101, "ROLE_TYPE_NULL"),
    ROLE_PERMISSIONS_NULL(102, "ROLE_PERMISSIONS_NULL"),
    ROLE_ID_NULL(103, "ROLE_ID_NULL"),
    ROLE_NOT_FOUND(404, "ROLE_NOT_FOUND"),

    PERMISSION_NULL(1, "PERMISSION_NULL"),
    PERMISSION_REQUIRED_FIELD_NULL(100, "PERMISSION_REQUIRED_FIELD_NULL"),
    PERMISSION_TYPE_NULL(101, "PERMISSION_TYPE_NULL"),
    PERMISSION_ID_NULL(102, "PERMISSION_ID_NULL"),
    PERMISSION_NOT_FOUND(404, "PERMISSION_NOT_FOUND"),

    BUG_NULL(1, "BUG_NULL"),
    BUG_REQUIRED_FIELD_NULL(100, "BUG_REQUIRED_FIELD_NULL"),
    BUG_TITLE_NULL(101, "BUG_TITLE_NULL"),
    BUG_ASSIGNED_TO_NULL(102, "BUG_ASSIGNED_TO_NULL"),
    BUG_TARGET_DATE_NULL(103, "BUG_TARGET_DATE_NULL"),
    BUG_FIXED_VERSION_NULL(104, "BUG_FIXED_VERSION_NULL"),
    BUG_VERSION_NULL(105, "BUG_VERSION_NULL"),
    BUG_SEVERITY_NULL(106, "BUG_SEVERITY_NULL"),
    BUG_STATUS_NULL(107, "BUG_STATUS_NULL"),
    BUG_CREATED_BY_NULL(108, "CREATED_BY_NULL"),
    BUG_ID_NULL(109, "BUG_ID_NULL"),
    BUG_COMPLEX_VALIDATION_FAIL(200, "BUG_COMPLEX_VALIDATION_FAIL"),
    BUG_STATUS_INCOMPATIBLE(201, "BUG_STATUS_INCOMPATIBLE"),
    BUG_DESCRIPTION_TOO_SHORT(202, "BUG_DESCRIPTION_TOO_SHORT"),
    BUG_VERSION_NOT_VALID(203, "BUG_VERSION_NOT_VALID"),
    BUG_ATTACHMENT_NOT_ON_SERVER(204, "BUG_ATTACHMENT_NOT_ON_SERVER"),
    BUG_FILE_TOO_BIG(205, "BUG_FILE_TOO_BIG"),
    BUG_EXTENSION_NOT_VALID(206, "BUG_EXTENSION_NOT_VALID"),
    BUG_COULD_NOT_WRITE_FILE(207, "BUG_COULD_NOT_WRITE_FILE"),
    BUG_SEVERITY_NOT_VALID(208, "BUG_SEVERITY_NOT_VALID"),
    BUG_TARGET_DATE_NOT_VALID(209, "BUG_TARGET_DATE_NOT_VALID"),
    BUG_COULD_NOT_DELETE_FILE(210, "BUG_COULD_NOT_DELETE_FILE"),
    BUG_FILE_NOT_VALID(211, "BUG_FILE_NOT_VALID"),
    BUG_NOT_FOUND(404, "BUG_NOT_FOUND"),

    NOTIFICATION_NOT_FOUND(404, "NOTIFICATION_NOT_FOUND");
    int id;
    String message;

    DetailedExceptionCode(int id, String message) {
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
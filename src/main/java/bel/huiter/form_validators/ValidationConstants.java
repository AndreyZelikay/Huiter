package bel.huiter.form_validators;

public class ValidationConstants {
    public static final Integer MAX_TWIT_BODY_LENGTH = 250;
    public static final Integer MAX_COMMENT_BODY_LENGTH = 250;
    public static final Integer MAX_TAG_BODY_LENGTH = 20;
    public static final Integer MAX_PASSWORD_LENGTH = 50;
    public static final Integer MAX_USER_NAME_LENGTH = 50;
    public static final String PASSWORD_REGEX = "^(?=.*[0-9]).{8, }$";
}

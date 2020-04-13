package bel.huiter.form_validators;

import bel.huiter.forms.UserRegistrationForm;

import java.util.ArrayList;
import java.util.List;

public class UserRegistrationValidator implements FormValidator<UserRegistrationForm> {
    @Override
    public List<String> validate(UserRegistrationForm form) {
        List<String> errors = new ArrayList<>();

        if(form.getName().isEmpty()
                || form.getName().length() > ValidationConstants.MAX_USER_NAME_LENGTH) {
            errors.add("invalid user name");
        } else if(form.getPassword().isEmpty()
                || form.getPassword().length() > ValidationConstants.MAX_PASSWORD_LENGTH
                || !form.getPassword().matches(ValidationConstants.PASSWORD_REGEX)) {
            errors.add("invalid password");
        }

        return errors;
    }
}

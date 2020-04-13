package bel.huiter.form_validators;

import bel.huiter.forms.CommentForm;

import java.util.ArrayList;
import java.util.List;

public class CommentValidator implements FormValidator<CommentForm> {
    @Override
    public List<String> validate(CommentForm form) {
        List<String> errors = new ArrayList<>();

        if(form.getBody().isEmpty() || form.getBody().length() > ValidationConstants.MAX_COMMENT_BODY_LENGTH) {
            errors.add("invalid comment body");
        }

        return errors;
    }
}

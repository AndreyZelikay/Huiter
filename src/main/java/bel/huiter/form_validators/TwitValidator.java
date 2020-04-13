package bel.huiter.form_validators;

import bel.huiter.forms.TwitForm;

import java.util.ArrayList;
import java.util.List;

public class TwitValidator implements FormValidator<TwitForm> {

    @Override
    public List<String> validate(TwitForm form) {
        List<String> errors = new ArrayList<>();

        if(form.getBody().isEmpty() || form.getBody().length() > ValidationConstants.MAX_TWIT_BODY_LENGTH) {
            errors.add("invalid post body");
        }

        form.getTags().forEach(tag -> {
            if(tag.getBody().isEmpty() ||
                    tag.getBody().length() > ValidationConstants.MAX_TAG_BODY_LENGTH) {
                errors.add("invalid tag:" + tag.getBody());
            }
        });

        return errors;
    }
}

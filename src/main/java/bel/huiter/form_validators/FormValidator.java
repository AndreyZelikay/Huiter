package bel.huiter.form_validators;

import java.util.List;

public interface FormValidator<T> {
    List<String> validate(T form);
}

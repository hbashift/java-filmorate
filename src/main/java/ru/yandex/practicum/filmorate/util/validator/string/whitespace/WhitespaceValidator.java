package ru.yandex.practicum.filmorate.util.validator.string.whitespace;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WhitespaceValidator implements ConstraintValidator<Whitespace, String> {
    boolean flag;

    @Override
    public void initialize(Whitespace constraintAnnotation) {
        flag = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (flag)
            return value.contains(" ");
        else
            return !value.contains(" ");
    }
}

package ru.yandex.practicum.filmorate.util.validator.duration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class PositiveDurationValidator implements ConstraintValidator<PositiveDuration, Duration> {

    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        return !value.isNegative();
    }
}

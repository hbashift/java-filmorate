package ru.yandex.practicum.filmorate.util.validator.duration;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
* if annotated Duration is positive -> valid
* else -> non-valid
* */

@Documented
@Target({FIELD, ANNOTATION_TYPE, TYPE, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = PositiveDurationValidator.class)
public @interface PositiveDuration {
    String message() default "{PositiveDuration.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
